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
public class UserInfoCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SOMEONE_INFO_PAGE = PageResourceManager.getInstance().getValue("jsp.someoneInfo");
        final String USER_LOGIN = requestContent.getParameter("userLogin");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, SOMEONE_INFO_PAGE);

        try {
            User user = UserReceiver.getUserInfo(USER_LOGIN);
            if (user != null) {
                requestContent.setSessionAttribute("someone", user);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException(e);
        }

        return commandContent;
    }
}
