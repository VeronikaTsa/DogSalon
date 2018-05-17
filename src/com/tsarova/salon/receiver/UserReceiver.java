package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.UserRepository;
import com.tsarova.salon.specification.Specification;
import com.tsarova.salon.specification.impl.UserSpecificationByEmail;
import com.tsarova.salon.specification.impl.UserSpecificationByLogin;
import com.tsarova.salon.specification.impl.UserSpecificationInfoByLogin;
import com.tsarova.salon.specification.impl.UserSpecificationSignUp;
import com.tsarova.salon.util.Encrypting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class UserReceiver {
    private static Logger logger = LogManager.getLogger();

    static final String EMAIL_REGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c" +
                    "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                    "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
                    "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]" +
                    "|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                    "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    //поиск при логинации с find
    public static User defineUser(String email, String password) throws ReceiverException{
        User user = null;
        if (!email.isEmpty() && !password.isEmpty()){
            password = String.valueOf(Encrypting.md5Encrypt(password));
            Repository userRepository = new UserRepository();
            try {
                 user = (User) userRepository.find(new User(email, password)); //может optional
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return user;
    }

    public static User createUser(String email, String password, String login) throws ReceiverException {
        User user = null;
        try {
            password = String.valueOf(Encrypting.md5Encrypt(password));

            Repository userRepository = new UserRepository();
            Specification userSpecificationSignUp = new UserSpecificationSignUp(email, password, login, UserRole.USER);

            List<User> userList = null;
            userList = userRepository.query(userSpecificationSignUp);
            if(!userList.isEmpty()){
                System.out.println("userList: " + userList);
                user = (User) userList.remove(0);
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return user;
    }

    public static int canCreateUser(String email, String login, String password, String passwordRepeat,
                                    Map<String, String> map) throws ReceiverException{
        if(!email.isEmpty() && !password.isEmpty() && !passwordRepeat.isEmpty()
                && password.equals(passwordRepeat) && email.matches(EMAIL_REGEX) && login.length()>3 &&
                password.length() > 3){
            if(!ifUserExist(email) ){
                if(!ifLoginExist(login))
                {
                    return sendEmail(email);
                }else{
                    map.put("login", "Такой логин уже есть");
                }
            }
        }
        System.    out.println("НАДО " + map.get("email"));
        if(!password.equals(passwordRepeat)){
            map.put("passwordRepeat", "Неверно введен пароль");
        }
        if(!email.matches(EMAIL_REGEX)){
            if(email.isEmpty()){
                map.put("email", "Введите email");
                System.out.println("ПУСТО");
            }else{
                map.put("email", "Некорректный email");
            }
        }
        if(password.isEmpty()){
            map.put("password", "Введите пароль");
        }else if(password.length()<4){
            map.put("password", "Введите больше трех символов");
        }
        if(passwordRepeat.isEmpty()){
            map.put("passwordRepeat", "Повторите пароль");
        }
        if(login.isEmpty()){
            map.put("login", "Введите логин");
        }else if(login.length()<4){
            map.put("login", "Введите больше трех символов");
        }
        return 0;
    }

    public static boolean createUser(String email, String login, UserRole userRole) throws ReceiverException{
        User user;

        if(email != null && login!= null && email.matches(EMAIL_REGEX) && login.length()>3){
            System.out.println("в полях не пусто и мэил норм");
            if(!ifLoginExist(login)){//??????????????

                Repository userRepository = new UserRepository();
                Specification userSpecificationSignUp = new UserSpecificationSignUp(email,
                        String.valueOf(Encrypting.md5Encrypt(String.valueOf(sendEmail(email)))), login, userRole);

                List<User> userList = null;
                try {
                    userList = userRepository.query(userSpecificationSignUp);
                } catch (RepositoryException e) {
                    logger.catching(Level.ERROR, e);
                    throw new ReceiverException(e);
                }
                if(!userList.isEmpty()){
                    System.out.println("userList: " + userList);
                    user = (User) userList.remove(0);
                    return true;
                }
            }
        }
        System.out.println("может в полях пусто или мэил не норм или такой чел есть в бд");
        return false;
    }

    private static boolean ifUserExist(String email) throws ReceiverException {
        Repository userRepository = new UserRepository();
        Specification userSpecificationByEmail = new UserSpecificationByEmail(email);
        return ifExist(email, userSpecificationByEmail);

    }

    private static boolean ifLoginExist(String login) throws ReceiverException {
        Specification userSpecificationByLogin = new UserSpecificationByLogin(login);
        return ifExist(login, userSpecificationByLogin);
    }

    private static boolean ifExist(String checkingName, Specification specification) throws ReceiverException {
        Repository userRepository = new UserRepository();
        try {
            List<User> userList = userRepository.query(specification); //может optional
            if(userList != null){
                System.out.println("такого в бд нет");
                return false;
            }else {
                System.out.println("Такой есть в бд");
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return true;
    }

    public static User getUserInfo(String login)throws ReceiverException{
        Repository userRepository = new UserRepository();


        Specification userSpecificationInfoByLogin = new UserSpecificationInfoByLogin(login);
        try {
            List<User> userList = userRepository.query(userSpecificationInfoByLogin); //может optional
            if(!userList.isEmpty()){
                System.out.println("всё отл, получили инфу");
                return userList.remove(0);//???????????   ой ли

            }else {
                System.out.println("беда-беда, не получили юзера");
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return null; //optional

    }

    private static int sendEmail(String emailToSend) {
        final String fromUsername = "sempiternalSatellite@gmail.com"; // input some email
        final String password = "sempiternaluniverse"; //input password


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
                    InternetAddress.parse(emailToSend)); //input email to send

            message.setSubject("Happy Puppy beauty salon");
            int code = generateCode(); //я молодец, он никуда отсюда не уйдет, пока не зарегается больше РЕТЕНШН - меньше churn rate
            message.setText("Your code: " + code);
            Transport.send(message);
            return code;
        } catch (MessagingException e) {
            logger.catching(Level.ERROR, e);
            return 1;
        }
        //return 0; //???????????
    }

    private static int generateCode(){
        Random random = new Random(System.currentTimeMillis());
        int randomNumber = 1000 + random.nextInt(9000);
        System.out.println("Your code: " + randomNumber);
        return randomNumber;
    }
}