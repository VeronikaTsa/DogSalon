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

public class SignUpExpertCommand implements Command{
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                PageResourceManager.getInstance().getValue("jsp.adminPanel"));

        try {
            if(UserReceiver.createUser(requestContent.getParameter("email"), requestContent.getParameter("login"),
                    UserRole.EXPERT)){

                requestContent.setAttribute("signUpExpertSuccess", "специалист зареган");

            }

        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        requestContent.setAttribute("signUpExpertSuccess", "специалист не был зареган");

        return commandContent;
    }
}
