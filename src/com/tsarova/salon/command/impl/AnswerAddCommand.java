package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.AnswerReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Veronika Tsarova
 */
public class AnswerAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String ANSWER_ADD_PAGE = PageResourceManager.getInstance().getValue("jsp.answerAdd");
        final String QUESTION_NOT_ANSWERED_PAGE =
                PageResourceManager.getInstance().getValue("jsp.questionNotAnswered");
        final Long QUESTION_ID = Long.valueOf(requestContent.getParameter("questionId"));
        final String ANSWER_CONTENT = requestContent.getParameter("answer");

        User sessionUser = (User) requestContent.getSessionAttribute("user");
        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, ANSWER_ADD_PAGE);

        try {
            if (AnswerReceiver.addAnswer(QUESTION_ID, ANSWER_CONTENT, sessionUser)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, QUESTION_NOT_ANSWERED_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
