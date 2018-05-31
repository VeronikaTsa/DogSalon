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

/**
 * @author Veronika Tsarova
 */
public class UserUpdateCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String USER_EDIT_PAGE = PageResourceManager.getInstance().getValue("jsp.userEdit");
        final String USER_INFO_PAGE = PageResourceManager.getInstance().getValue("jsp.userInfo");
        final String NEW_USER_EMAIL = requestContent.getParameter("emailToEdit");
        final String NEW_USER_LOGIN = requestContent.getParameter("loginToEdit");
        final String NEW_USER_FIRST_NAME = requestContent.getParameter("firstNameToEdit");
        final String NEW_USER_LAST_NAME = requestContent.getParameter("lastNameToEdit");
        final String NEW_USER_TELEPHONE = requestContent.getParameter("telephoneToEdit");
        final String NEW_USER_BIRTHDAY = requestContent.getParameter("birthdayToEdit");
        final String NEW_USER_SEX = requestContent.getParameter("sexToEdit");
        final String OLD_USER_PASSWORD = requestContent.getParameter("oldPassword");
        final String NEW_USER_PASSWORD = requestContent.getParameter("newPassword");

        User sessionUser = (User) requestContent.getSessionAttribute("user");
        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, USER_EDIT_PAGE);

        try {
            if (UserReceiver.updateUser(sessionUser.getUserId(),
                    NEW_USER_EMAIL,
                    NEW_USER_LOGIN,
                    NEW_USER_FIRST_NAME,
                    NEW_USER_LAST_NAME,
                    NEW_USER_TELEPHONE,
                    NEW_USER_BIRTHDAY,
                    NEW_USER_SEX,
                    OLD_USER_PASSWORD,
                    NEW_USER_PASSWORD,
                    sessionUser)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, USER_INFO_PAGE);
                requestContent.setSessionAttribute("user", sessionUser);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
