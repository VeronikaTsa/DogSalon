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
 * The class {@code AnswerAddCommand} implements
 * {@code Command} that indicates different reactions to {@code ServletController}
 * or {@code ServletUploader} request.
 *
 * <p>The class {@code AnswerAddCommand} decides how response should look like.
 *
 * @author Veronika Tsarova
 * @see com.tsarova.salon.command.Command
 */
public class AnswerAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    /**
     * This method takes parameters from received {@param requestContent}, then
     * in case of successful adding an answer redirects to page with not answered questions.
     *
     * @param requestContent contains parameters and attributes from request
     * @return {@code CommandContent} which contains next page path and type of response
     * @throws CommandException when receives {@code ReceiverException}
     * @see com.tsarova.salon.content.CommandContent
     * @see com.tsarova.salon.exception.ReceiverException
     */
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
