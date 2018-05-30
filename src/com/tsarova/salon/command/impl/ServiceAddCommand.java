package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Service;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.FeedbackReceiver;
import com.tsarova.salon.receiver.ServiceReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ServiceAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.adminPanel"));

        requestContent.setAttribute("serviceAddSuccess", "не была добавлена");
        try {
            if(ServiceReceiver.addService(requestContent.getParameter("name"),
                    requestContent.getParameter("content"),
                    Double.valueOf(requestContent.getParameter("price")).doubleValue())) {
                requestContent.setAttribute("serviceAddSuccess", "Услуга была добавлена");
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        return commandContent;
    }
}
