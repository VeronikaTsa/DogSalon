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

/**
 * @author Veronika Tsarova
 */
public class ServiceAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String ADMIN_PANEL_PAGE = PageResourceManager.getInstance().getValue("jsp.adminPanel");
        final String SERVICE_NAME = requestContent.getParameter("name");
        final String SERVICE_CONTENT = requestContent.getParameter("content");
        final double SERVICE_PRICE = Double.valueOf(requestContent.getParameter("price"));

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.FORWARD, ADMIN_PANEL_PAGE);

        try {
            if (ServiceReceiver.addService(SERVICE_NAME, SERVICE_CONTENT, SERVICE_PRICE)) {
                commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, ADMIN_PANEL_PAGE);
            }
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }

        return commandContent;
    }
}
