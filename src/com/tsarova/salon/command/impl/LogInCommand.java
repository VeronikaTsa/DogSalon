package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.UserReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogInCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String LOGIN_PAGE = PageResourceManager.getInstance().getValue("jsp.logIn");
    private static final String INDEX_PAGE = PageResourceManager.getInstance().getValue("jsp.index");



    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, LOGIN_PAGE);
        final String USER_EMAIL = requestContent.getParameter("email");
        final String USER_PASSWORD = requestContent.getParameter("password");
        try {
            User user = UserReceiver.defineUser(USER_EMAIL, USER_PASSWORD);

            if (user != null) {//заменить на optional
                requestContent.setSessionAttribute("user", user);
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, INDEX_PAGE);
            } else {
                logger.log(Level.INFO, "No user with email " + requestContent.getParameter("email") +
                        " or password is incorrect");
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException(e);
        }
        return commandContent;
    }
}


//        try {
//            //проверка атрибутов на налл
//            User user = readUser(requestContent);
//            //HttpSession session = request.getSession();
//            if (!UserReceiver.defineUser(user)) {
//                /*request.setAttribute(Attributes.ERROR_LOGIN_PASS_MESSAGE,
//                        Message.ERROR_LOGIN_PASS_MESSAGE);
//                request.setAttribute(
//                        Attributes.PAGE,
//                        ConfigurationManager.getInstance().getProperty(
//                                ConfigurationManager.LOGIN_PAGE_PATH));*/
//                System.out.println("нет такого");
//
//            } else {
//                /*rememberUser(user, request);
//                switch (user.getRole()) {
//                    case "admin":
//                        request.setAttribute(
//                                Attributes.PAGE,
//                                ConfigurationManager.getInstance().getProperty(
//                                        ConfigurationManager.ADMIN_PAGE_PATH));
//                        session.setAttribute(
//                                Attributes.PAGE,
//                                ConfigurationManager.getInstance().getProperty(
//                                        ConfigurationManager.ADMIN_PAGE_PATH));
//                        return ConfigurationManager.getInstance().getProperty(
//                                ConfigurationManager.ADMIN_PAGE_PATH);
//
//                    case "entrant":
//                        request.setAttribute(
//                                Attributes.PAGE,
//                                ConfigurationManager.getInstance().getProperty(
//                                        ConfigurationManager.MAIN_PAGE_PATH));
//                        session.setAttribute(
//                                Attributes.PAGE,
//                                ConfigurationManager.getInstance().getProperty(
//                                        ConfigurationManager.MAIN_PAGE_PATH));
//                        return ConfigurationManager.getInstance().getProperty(
//                                ConfigurationManager.MAIN_PAGE_PATH);
//
//                    default:
//                        return ConfigurationManager.getInstance().getProperty(
//                                ConfigurationManager.REGISTER_USER_PAGE_PATH);
//                }*/
//                System.out.println("есть такой");
//            }
//        } catch (ReceiverException e) {
//            e.printStackTrace();
//        }
