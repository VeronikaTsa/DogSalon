package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.Question;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.receiver.QuestionReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class QuestionDeleteCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.questionNotAnswered"));
        System.out.println(requestContent.getParameter("id"));
        requestContent.setAttribute("questionDelSuccess", "Вопрос не был удален");
        try {

            if(QuestionReceiver.delQuestion(Long.valueOf(requestContent.getParameter("id")))){
                requestContent.setAttribute("questionDelSuccess", "Вопрос был удален");
            }
            List<Question> questionNotAnsweredList;
            questionNotAnsweredList = QuestionReceiver.receiveQuestionNotAnsweredList();
            requestContent.setAttribute("questionNotAnsweredList", questionNotAnsweredList);
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
