package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.*;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.UserRepository;
import com.tsarova.salon.specification.Specification;
import com.tsarova.salon.specification.impl.UserSpecificationByEmailOrLogin;
import com.tsarova.salon.specification.impl.UserSpecificationInfoByLogin;
import com.tsarova.salon.util.Encrypting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.util.*;

/**
 * @author Veronika Tsarova
 */
public class UserReceiver {
    private static Logger logger = LogManager.getLogger();

    private static final String EMAIL_REGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c" +
                    "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                    "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
                    "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]" +
                    "|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                    "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    public static User defineUser(String email, String password) throws ReceiverException {
        User user = null;
        if (!email.isEmpty() && !password.isEmpty()) {
            password = Encrypting.md5Encrypt(password);
            Repository<User> userRepository = new UserRepository();
            try {
                user = userRepository.find(new User(email, password));
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return user;
    }

    public static User createUser(String email, String password, String login) throws ReceiverException {
        User user = null;
        if (!email.isEmpty() && !password.isEmpty() && !login.isEmpty()) {
            try {
                password = Encrypting.md5Encrypt(password);
                user = new User(email, password, login, UserRole.USER);
                Repository<User> userRepository = new UserRepository();
                if (userRepository.add(user)) {
                    logger.log(Level.DEBUG, "User " + email + " added");
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return user;
    }

    public static int canCreateUser(String email, String login, String password, String passwordRepeat,
                                    Map<String, String> map) throws ReceiverException {
        if (!email.isEmpty() && !password.isEmpty() && !passwordRepeat.isEmpty()
                && password.equals(passwordRepeat) && email.matches(EMAIL_REGEX) && login.length() > 3 &&
                password.length() > 3) {
            if (!ifUserExist(email)) {
                if (!ifLoginExist(login)) {
                    return sendEmail(email);
                } else {
                    map.put("login", "Такой логин уже есть");
                }
            }
        }
        if (!password.equals(passwordRepeat)) {
            map.put("passwordRepeat", "Неверно введен пароль");
        }
        if (!email.matches(EMAIL_REGEX)) {
            if (email.isEmpty()) {
                map.put("email", "Введите email");
            } else {
                map.put("email", "Некорректный email");
            }
        }
        if (password.isEmpty()) {
            map.put("password", "Введите пароль");
        } else if (password.length() < 4) {
            map.put("password", "Введите больше трех символов");
        }
        if (passwordRepeat.isEmpty()) {
            map.put("passwordRepeat", "Повторите пароль");
        }
        if (login.isEmpty()) {
            map.put("login", "Введите логин");
        } else if (login.length() < 4) {
            map.put("login", "Введите больше трех символов");
        }
        return 0;
    }

    public static boolean createUser(String email, String login, UserRole userRole) throws ReceiverException {
        User user;
        if (!email.isEmpty() && !login.isEmpty() && email.matches(EMAIL_REGEX) && login.length() > 3) {
            if (!ifLoginExist(login)) {
                String password = Encrypting.md5Encrypt(String.valueOf(sendEmail(email)));
                user = new User(email, password, login, userRole);
                Repository<User> userRepository = new UserRepository();
                try {
                    if (userRepository.add(user)) {
                        logger.log(Level.DEBUG, userRole + " " + email + " added");
                        return true;
                    }
                } catch (RepositoryException e) {
                    logger.catching(Level.ERROR, e);
                    throw new ReceiverException(e);
                }
            }
        }
        return false;
    }

    public static boolean updateUser(Long id, String emailToEdit, String loginToEdit, String firstNameToEdit,
                                     String lastNameToEdit, String telephoneToEdit, String birthdayToEdit,
                                     String sexToEdit, String oldPassword, String newPassword,
                                     User sessionUser) throws ReceiverException {
        Repository<User> userRepository = new UserRepository();
        User user;
        UserContent userContent;

        if (!emailToEdit.isEmpty() && !loginToEdit.isEmpty()) {
            user = new User(id, emailToEdit, loginToEdit, sessionUser.getPassword());
            if (!firstNameToEdit.isEmpty() || !lastNameToEdit.isEmpty() || !telephoneToEdit.isEmpty() ||
                    !birthdayToEdit.isEmpty() || !"none".equals(sexToEdit)) {
                userContent = new UserContent();
                if (!firstNameToEdit.isEmpty()) {
                    userContent.setFirstName(firstNameToEdit);
                }
                if (!lastNameToEdit.isEmpty()) {
                    userContent.setLastName(lastNameToEdit);
                }
                if (!telephoneToEdit.isEmpty()) {
                    userContent.setTelephone(telephoneToEdit);
                }
                if (!birthdayToEdit.isEmpty()) {
                    userContent.setBirthday(Date.valueOf(birthdayToEdit));
                }
                if (!"none".equals(sexToEdit)) {
                    userContent.setSex(UserSex.valueOf(sexToEdit.toUpperCase()));
                }
                user.setUserContent(userContent);
            }
            if (!oldPassword.isEmpty() &&
                    !newPassword.isEmpty()) {
                if (Encrypting.md5Encrypt(oldPassword).equals(sessionUser.getPassword()) &&
                        !newPassword.equals(oldPassword)) {
                    user.setPassword(Encrypting.md5Encrypt(newPassword));
                } else {
                    return false;
                }
            }

            try {
                return updateAndSetNewUser(userRepository, user, sessionUser);
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static User getUserInfo(String login) throws ReceiverException {
        Repository<User> userRepository = new UserRepository();
        Specification<User> userSpecificationInfoByLogin = new UserSpecificationInfoByLogin(login);
        try {
            List<User> userList = userRepository.query(userSpecificationInfoByLogin);
            if (!userList.isEmpty()) {
                return userList.remove(0);

            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return null;
    }

    private static boolean updateAndSetNewUser(Repository<User> userRepository, User newUser, User sessionUser)
            throws RepositoryException {
        if (userRepository.update(newUser)) {
            sessionUser.setLogin(newUser.getLogin());
            sessionUser.setEmail(newUser.getEmail());
            sessionUser.setPassword(newUser.getPassword());
            sessionUser.setUserContent(newUser.getUserContent());
            return true;
        }
        return false;
    }

    private static boolean ifUserExist(String email) throws ReceiverException {
        Specification<User> userSpecificationByEmailOrLogin = new UserSpecificationByEmailOrLogin(email,
                UserSpecificationByEmailOrLogin.RequiredParameterType.EMAIL);
        return ifExist(userSpecificationByEmailOrLogin);
    }

    private static boolean ifLoginExist(String login) throws ReceiverException {
        Specification<User> userSpecificationByEmailOrLogin = new UserSpecificationByEmailOrLogin(login,
                UserSpecificationByEmailOrLogin.RequiredParameterType.LOGIN);
        return ifExist(userSpecificationByEmailOrLogin);
    }

    private static boolean ifExist(Specification specification) throws ReceiverException {
        Repository<User> userRepository = new UserRepository();
        try {
            Optional<List<User>> userList = Optional.ofNullable(userRepository.query(specification));
            if (!userList.isPresent()) {
                return true;
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return false;
    }

    private static int sendEmail(String emailToSend) {
        final String fromUsername = "sempiternalSatellite@gmail.com";
        final String password = "sempiternaluniverse";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromUsername, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUsername));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailToSend));

            message.setSubject("Happy Puppy beauty salon");
            int code = generateCode();
            message.setText("Your code: " + code);
            Transport.send(message);
            return code;
        } catch (MessagingException e) {
            logger.catching(Level.ERROR, e);
            return 1;
        }
    }

    private static int generateCode() {
        Random random = new Random(System.currentTimeMillis());
        int randomNumber = 1000 + random.nextInt(9000);
        logger.log(Level.DEBUG, "Your code: " + randomNumber);
        return randomNumber;
    }
}