package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.resource.page.PageResourceManager;

/**
 * @author Veronika Tsarova
 */
public class LogOutCommand implements Command {
    @Override
    public CommandContent execute(RequestContent requestContent) {
        final String INDEX_PAGE = PageResourceManager.getInstance().getValue("jsp.index");

        CommandContent commandContent = new CommandContent(CommandContent.ResponseType.REDIRECT, INDEX_PAGE);
        requestContent.removeSessionAttribute("user");

        return commandContent;
    }
}
