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

import java.util.ArrayList;
import java.util.List;

public class FeedbackCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.INCLUDE,
                PageResourceManager.getInstance().getValue("jsp.feedbackAll"));
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            feedbackList = FeedbackReceiver.receiveFeedbackList();
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        System.out.println(feedbackList);
        requestContent.setAttribute("feedbackList", feedbackList);
        return commandContent;
    }
}
