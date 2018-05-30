package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.ServiceReceiver;
import com.tsarova.salon.receiver.UserReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateCommand implements Command{
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.userEdit"));
        Map<String, String> errorMap = new HashMap();
        try {
            User sessionUser = (User)requestContent.getSessionAttribute("user");
            if(UserReceiver.updateUser(Long.valueOf(String.valueOf(((User)requestContent.getSessionAttribute("user")).getUserId())),
                    requestContent.getParameter("emailToEdit"),
                    requestContent.getParameter("loginToEdit"),
                    requestContent.getParameter("firstNameToEdit"),
                    requestContent.getParameter("lastNameToEdit"),
                    requestContent.getParameter("telephoneToEdit"),
                    requestContent.getParameter("birthdayToEdit"),
                    requestContent.getParameter("sexToEdit"),
                    requestContent.getParameter("oldPassword"),
                    requestContent.getParameter("newPassword"),
                    sessionUser,
                    errorMap)) {
                commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                        PageResourceManager.getInstance().getValue("jsp.userInfo"));
                requestContent.setSessionAttribute("user", sessionUser);
            } else {
                requestContent.setAttribute("errorMap", errorMap);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        return commandContent;
    }
}
