package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class FeedbackCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String FEEDBACK_ALL_PAGE = PageResourceManager.getInstance().getValue("jsp.feedbackAll");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.INCLUDE, FEEDBACK_ALL_PAGE);
        List<Feedback> feedbackList;

        try {
            feedbackList = FeedbackReceiver.receiveFeedbackList();
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        requestContent.setAttribute("feedbackList", feedbackList);

        return commandContent;
    }
}