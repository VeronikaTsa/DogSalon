package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.UserReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignUpContinueCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                PageResourceManager.getInstance().getValue("jsp.signUpContinue"));
        System.out.println("введенный код: "+ requestContent.getParameter("codeToCompare"));
        System.out.println("ожидаемый код: "+ requestContent.getSessionAttribute("code"));//удалить
        if(requestContent.getParameter("codeToCompare")
                .equals(String.valueOf(requestContent.getSessionAttribute("code")))){
            try {
                User user = UserReceiver.createUser(String.valueOf(requestContent.getSessionAttribute("email")),
                        String.valueOf(requestContent.getSessionAttribute("password")),
                        String.valueOf(requestContent.getSessionAttribute("login")));
                if(user != null){
                    requestContent.setSessionAttribute("user", user);
                    commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                            PageResourceManager.getInstance().getValue("jsp.index"));
                }
            } catch (ReceiverException e) {
                logger.catching(Level.ERROR, e);
                throw new CommandException();
            }
        } else {
            System.out.println("код не совпадает");
        }
        return commandContent;
    }
}
