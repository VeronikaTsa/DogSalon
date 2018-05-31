package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Service;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.ServiceReceiver;
import com.tsarova.salon.resource.page.PageResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServiceCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        final String SERVICE_PAGE = PageResourceManager.getInstance().getValue("jsp.service");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.INCLUDE, SERVICE_PAGE);
        List<Service> serviceList;

        try {
            serviceList = ServiceReceiver.receiveServiceList();
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
            throw new CommandException();
        }
        logger.log(Level.DEBUG, serviceList);
        requestContent.setAttribute("serviceList", serviceList);

        return commandContent;
    }
}
