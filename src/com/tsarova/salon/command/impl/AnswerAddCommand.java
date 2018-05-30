package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.AnswerReceiver;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnswerAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.answerAdd"));
        requestContent.setAttribute("answerAddSuccess", "Ответ не был добавлен");
        System.out.println(requestContent.getParameter("answer"));
        System.out.println(requestContent.getParameter("questionId"));
        try {
            if(AnswerReceiver.addAnswer(Long.valueOf(requestContent.getParameter("questionId")),
                    requestContent.getParameter("answer"),
                    (User)requestContent.getSessionAttribute("user"))){
                commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.questionNotAnswered"));
                requestContent.setAttribute("answerAddSuccess", "Ответ был добавлен");
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
