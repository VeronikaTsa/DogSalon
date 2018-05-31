package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.UserReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Veronika Tsarova
 */
public class SignUpCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SIGN_UP_PAGE = PageResourceManager.getInstance().getValue("jsp.signUp");
        final String SIGN_UP_CONTINUE_PAGE = PageResourceManager.getInstance().getValue("jsp.signUpContinue");
        final String USER_EMAIL = requestContent.getParameter("email");
        final String USER_LOGIN = requestContent.getParameter("login");
        final String USER_PASSWORD = requestContent.getParameter("password");
        final String USER_PASSWORD_REPEAT = requestContent.getParameter("passwordRepeat");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, SIGN_UP_PAGE);
        Map<String, String> map = new HashMap<>();

        try {
            int code = UserReceiver.canCreateUser(USER_EMAIL, USER_LOGIN, USER_PASSWORD, USER_PASSWORD_REPEAT, map);
            if (code > 1) {
                requestContent.setSessionAttribute("code", code);
                requestContent.setSessionAttribute("email", requestContent.getParameter("email"));
                requestContent.setSessionAttribute("login", requestContent.getParameter("login"));
                requestContent.setSessionAttribute("password", requestContent.getParameter("password"));
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, SIGN_UP_CONTINUE_PAGE);
            } else if (code == 1) {
                requestContent.setAttribute("messageSendSuccess", "Sending message unsuccessful");
            } else {
                requestContent.setAttribute("map", map);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException(e);
        }

        return commandContent;
    }
}
