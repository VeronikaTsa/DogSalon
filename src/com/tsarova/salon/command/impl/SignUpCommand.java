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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class SignUpCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {

        CommandContent commandContent;
        Map<String, String> map = new HashMap<>();

        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.signUp"));

        try {
            int code = UserReceiver.canCreateUser(requestContent.getParameter("email"),
                    requestContent.getParameter("login"),
                    requestContent.getParameter("password"),
                    requestContent.getParameter("passwordRepeat"), map);
            if (code > 1) {
                requestContent.setSessionAttribute("code", code);
                requestContent.setSessionAttribute("email", requestContent.getParameter("email"));
                requestContent.setSessionAttribute("login", requestContent.getParameter("login"));
                requestContent.setSessionAttribute("password", requestContent.getParameter("password"));
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                        PageResourceManager.getInstance().getValue("jsp.signUpContinue"));

            } else if (code == 1) {
                requestContent.setAttribute("messageSendSuccess", "Sending message unsuccessful");
            } else {
                requestContent.setAttribute("map", map);
                requestContent.removeSessionAttribute("email");
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException(e);
        }
        return commandContent;
    }
}
