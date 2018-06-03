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

import java.util.Optional;

/**
 * @author Veronika Tsarova
 */
public class LogInCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String LOGIN_PAGE = PageResourceManager.getInstance().getValue("jsp.logIn");
        final String INDEX_PAGE = PageResourceManager.getInstance().getValue("jsp.index");
        final String USER_EMAIL = requestContent.getParameter("email");
        final String USER_PASSWORD = requestContent.getParameter("password");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, LOGIN_PAGE);

        try {
            Optional<User> user = Optional.ofNullable(UserReceiver.defineUser(USER_EMAIL, USER_PASSWORD));
            if (user.isPresent()) {
                requestContent.setSessionAttribute("user", user.get());
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, INDEX_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException(e);
        }

        return commandContent;
    }
}