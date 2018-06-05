package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
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
public class AnswerDeleteCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    /**
     * This method takes parameters from received {@param requestContent}, then
     * in case of successful deleting an answer redirects to page with answered questions.
     *
     * @param requestContent contains parameters and attributes from request
     * @return {@code CommandContent} which contains next page path and type of response
     * @throws CommandException when receives {@code ReceiverException}
     * @see com.tsarova.salon.content.CommandContent
     * @see com.tsarova.salon.exception.ReceiverException
     */
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String QUESTION_ANSWER_PAGE = PageResourceManager.getInstance().getValue("jsp.questionAnswer");
        final Long ANSWER_ID = Long.valueOf(requestContent.getParameter("id"));

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, QUESTION_ANSWER_PAGE);

        try {
            if (AnswerReceiver.removeAnswer(ANSWER_ID)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, QUESTION_ANSWER_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}