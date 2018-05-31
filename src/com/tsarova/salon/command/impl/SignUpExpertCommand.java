package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.UserReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Veronika Tsarova
 */
public class SignUpExpertCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SIGN_UP_EXPERT_PAGE = PageResourceManager.getInstance().getValue("jsp.signUpExpert");
        final String ADMIN_PANEL_PAGE = PageResourceManager.getInstance().getValue("jsp.adminPanel");
        final String EXPERT_EMAIL = requestContent.getParameter("email");
        final String EXPERT_LOGIN = requestContent.getParameter("login");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, SIGN_UP_EXPERT_PAGE);

        try {
            if (UserReceiver.createUser(EXPERT_EMAIL, EXPERT_LOGIN, UserRole.EXPERT)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, ADMIN_PANEL_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
