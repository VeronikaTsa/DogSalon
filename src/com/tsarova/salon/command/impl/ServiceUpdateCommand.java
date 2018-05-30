package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.ServiceReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ServiceUpdateCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        CommandContent commandContent;
        commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                PageResourceManager.getInstance().getValue("jsp.serviceEdit"));
        Map<String, String> errorMap = new HashMap();
        requestContent.setAttribute("serviceAddSuccess", "услуга не была изменена");
        try {
            if(ServiceReceiver.updateService(Long.valueOf(requestContent.getParameter("serviceId")),
                    requestContent.getParameter("nameToEdit"),
                    requestContent.getParameter("contentToEdit"),
                    String.valueOf(requestContent.getAttribute("pictureName")),
                    requestContent.getParameter("priceToEdit"),
                    errorMap)) {
                commandContent = new CommandContent(CommandContent.ResponseType.FORWARD,
                        PageResourceManager.getInstance().getValue("jsp.serviceInfo"));
                requestContent.setAttribute("serviceAddSuccess", "услуга была изменена");
            } else {
                requestContent.setAttribute("errorMap", errorMap);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        requestContent.setAttribute("id", requestContent.getParameter("serviceId"));
        requestContent.setAttribute("name", requestContent.getParameter("nameToEdit"));
        requestContent.setAttribute("content", requestContent.getParameter("contentToEdit"));
        requestContent.setAttribute("price", requestContent.getParameter("priceToEdit"));
        requestContent.setAttribute("picture", String.valueOf(requestContent.getAttribute("pictureName")));


        return commandContent;
    }
}
