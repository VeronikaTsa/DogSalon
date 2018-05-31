package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Veronika Tsarova
 */
public class FeedbackAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String FEEDBACK_ADD_PAGE = PageResourceManager.getInstance().getValue("jsp.feedbackAdd");
        final String FEEDBACK_PAGE = PageResourceManager.getInstance().getValue("jsp.feedback");
        final String FEEDBACK_CONTENT = requestContent.getParameter("feedback");

        User sessionUser = (User) requestContent.getSessionAttribute("user");
        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, FEEDBACK_ADD_PAGE);

        try {
            if (FeedbackReceiver.addFeedback(FEEDBACK_CONTENT, sessionUser)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, FEEDBACK_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
