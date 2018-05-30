package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FeedbackAddCommand implements Command{
    private static Logger logger = LogManager.getLogger();
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.feedbackAdd"));
        requestContent.setAttribute("feedbackAddSuccess", "Отзыв не был добавлен");
        try {
            if(FeedbackReceiver.addFeedback(requestContent.getParameter("feedback"),
                    (User)requestContent.getSessionAttribute("user"))){
                requestContent.setAttribute("feedbackAddSuccess", "Отзыв был добавлен");
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
