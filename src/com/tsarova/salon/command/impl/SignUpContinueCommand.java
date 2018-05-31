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
public class SignUpContinueCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SIGN_UP_CONTINUE_PAGE = PageResourceManager.getInstance().getValue("jsp.signUpContinue");
        final String INDEX_PAGE = PageResourceManager.getInstance().getValue("jsp.index");
        final String SIGN_UP_CODE = String.valueOf(requestContent.getSessionAttribute("code"));
        final String CODE_TO_COMPARE = requestContent.getParameter("codeToCompare");
        final String USER_SESSION_EMAIL = String.valueOf(requestContent.getSessionAttribute("email"));
        final String USER_SESSION_PASSWORD = String.valueOf(requestContent.getSessionAttribute("password"));
        final String USER_SESSION_LOGIN = String.valueOf(requestContent.getSessionAttribute("login"));

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, SIGN_UP_CONTINUE_PAGE);

        if (CODE_TO_COMPARE.equals(SIGN_UP_CODE)) {
            try {
                User user = UserReceiver.createUser(USER_SESSION_EMAIL, USER_SESSION_PASSWORD, USER_SESSION_LOGIN);
                if (user != null) {
                    requestContent.setSessionAttribute("user", user);
                    commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, INDEX_PAGE);
                } else {
                    requestContent.removeSessionAttribute("email");
                    requestContent.removeSessionAttribute("login");
                    requestContent.removeSessionAttribute("password");
                    requestContent.removeSessionAttribute("code");
                }
            } catch (ReceiverException e) {
                logger.catching(Level.ERROR, e);
                throw new CommandException();
            }
        }

        return commandContent;
    }
}
