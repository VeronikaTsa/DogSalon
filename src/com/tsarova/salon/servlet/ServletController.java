package com.tsarova.salon.servlet;



import com.tsarova.salon.command.Command;
import com.tsarova.salon.command.CommandEnum;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Veronika Tsarova
 */
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            RequestContent requestContent = new RequestContent();
            requestContent.extractValues(request);
            CommandContent commandContent = chooseCommand(requestContent);
            requestContent.insertValues(request);
            sendResponse(commandContent, request, response);
        } catch (CommandException e) {
            logger.catching(Level.ERROR, e);
            request.getRequestDispatcher("/jsp/error/error.jsp").include(request, response);
        }
    }

    private CommandContent chooseCommand(RequestContent requestContent) throws CommandException {
        String commandName = requestContent.getParameter("command");
        CommandEnum commandEnumName = CommandEnum.valueOf(commandName.toUpperCase());
        Command command = commandEnumName.getValue();

        return command.execute(requestContent);
    }

    private void sendResponse(CommandContent commandContent, HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        switch (commandContent.getResponseType()) {
            case FORWARD:
                getServletContext().getRequestDispatcher(commandContent.getNextPage()).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(commandContent.getNextPage());
                break;
            case INCLUDE:
                request.getRequestDispatcher(commandContent.getNextPage()).include(request, response);
                break;
        }
    }
}

