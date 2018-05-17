package com.tsarova.salon.command;

import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandContent execute(RequestContent requestContent) throws CommandException;
}
