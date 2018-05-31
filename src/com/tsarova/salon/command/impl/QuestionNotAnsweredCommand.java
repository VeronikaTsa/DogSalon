package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Question;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.QuestionReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class QuestionNotAnsweredCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String QUESTION_NOT_ANSWERED_ALL_PAGE =
                PageResourceManager.getInstance().getValue("jsp.questionNotAnsweredAll");

        CommandContent commandContent =
                new CommandContent(CommandContent.ResponseType.INCLUDE, QUESTION_NOT_ANSWERED_ALL_PAGE);
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
