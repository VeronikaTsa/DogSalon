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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Veronika Tsarova
 */
public class ServiceUpdateCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SERVICE_EDIT_PAGE = PageResourceManager.getInstance().getValue("jsp.serviceEdit");
        final Long SERVICE_ID = Long.valueOf(requestContent.getParameter("serviceId"));
        final String NEW_SERVICE_NAME = requestContent.getParameter("nameToEdit");
        final String NEW_SERVICE_CONTENT = requestContent.getParameter("contentToEdit");
        final String NEW_SERVICE_PICTURE_NAME = String.valueOf(requestContent.getAttribute("pictureName"));
        final String NEW_SERVICE_PRICE = requestContent.getParameter("priceToEdit");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, SERVICE_EDIT_PAGE);
        Map<String, String> errorMap = new HashMap<>();

        try {
            if (ServiceReceiver.updateService(SERVICE_ID, NEW_SERVICE_NAME, NEW_SERVICE_CONTENT,
                    NEW_SERVICE_PICTURE_NAME, NEW_SERVICE_PRICE, errorMap)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                        PageResourceManager.getInstance().getValue("jsp.serviceInfo")
                                + "?name=" + URLEncoder.encode(NEW_SERVICE_NAME, "UTF-8") +
                                "&id=" + URLEncoder.encode(String.valueOf(SERVICE_ID), "UTF-8") +
                                "&content=" + URLEncoder.encode(NEW_SERVICE_CONTENT, "UTF-8") +
                                "&price=" + URLEncoder.encode(NEW_SERVICE_PRICE, "UTF-8") +
                                "&picture=" + URLEncoder.encode(NEW_SERVICE_PICTURE_NAME, "UTF-8"));
            } else {
                requestContent.setAttribute("errorMap", errorMap);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        } catch (UnsupportedEncodingException e) {
            logger.catching(Level.ERROR, e);
        }

        return commandContent;
    }
}
