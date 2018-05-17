package com.tsarova.salon.command.impl;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;

public class FeedbackUserCommand implements Command {
    @Override
    public CommandContent execute(RequestContent requestContent) throws CommandException {
        return null;
    }
}
