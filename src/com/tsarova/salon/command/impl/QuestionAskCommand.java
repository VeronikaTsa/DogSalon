package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.receiver.QuestionReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;

public class QuestionAskCommand implements Command {
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {

        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.questionAsk"));
        requestContent.setAttribute("questionAskSuccess", "Вопрос не был отправлен");
        try {
            if(QuestionReceiver.addQuestion(requestContent.getParameter("question"),
                    (User)requestContent.getSessionAttribute("user"))){
                requestContent.setAttribute("questionAskSuccess", "Вопрос был отправлен");
            }
        } catch (ReceiverException e) {
            e.printStackTrace();
        }

        return commandContent;
    }
}
