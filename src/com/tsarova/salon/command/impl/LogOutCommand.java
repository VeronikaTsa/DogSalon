package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.resource.page.PageResourceManager;

public class LogOutCommand implements Command {
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        requestContent.removeSessionAttribute("user");
        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT,
                PageResourceManager.getInstance().getValue("jsp.index"));
        return commandContent;
    }
}
