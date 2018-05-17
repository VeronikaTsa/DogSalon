package com.tsarova.salon.command.impl;

import com.sun.deploy.trace.LoggerTraceListener;
import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Answer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.questionNotAnswered"));



        List<Question> questionNotAnsweredList;
        try {
            questionNotAnsweredList = QuestionReceiver.receiveQuestionNotAnsweredList();
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        requestContent.setAttribute("questionNotAnsweredList", questionNotAnsweredList);


        return commandContent;
    }
}
