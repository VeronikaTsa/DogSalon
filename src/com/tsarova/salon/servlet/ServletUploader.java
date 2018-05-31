package com.tsarova.salon.servlet;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.command.CommandEnum;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

import static jdk.internal.dynalink.support.NameCodec.encode;

/**
 * @author Veronika Tsarova
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5 * 5)
@WebServlet("/ServletUploader")
public class ServletUploader extends HttpServlet {
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
        RequestContent requestContent = new RequestContent();
        final String UPLOAD_DIR = chooseUploadDir(request.getParameter("command"));

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists() && !fileSaveDir.mkdir()) {
            logger.log(Level.ERROR, "The directory wasn't created, along with all necessary parent directories");
        }
        requestContent.extractValues(request);
        requestContent.setAttribute("pictureName", request.getParameter("pictureName"));
        try {
            for (Part part : request.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                    requestContent.setAttribute("pictureName", part.getSubmittedFileName());
                }
            }
        } catch (IOException e) {
            logger.catching(Level.ERROR, e);
        }
        CommandContent commandContent;
        try {
            commandContent = chooseCommand(requestContent);
            requestContent.insertValues(request);
            sendResponse(commandContent, request, response);
        } catch (CommandException e) {
            logger.catching(Level.ERROR, e);
            request.getRequestDispatcher("/jsp/error/error.jsp").include(request, response);
        }
    }

    private void sendResponse(CommandContent commandContent, HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        switch (commandContent.getResponseType()) {
            case FORWARD:
                getServletContext().getRequestDispatcher(commandContent.getNextPage()).forward(request, response);
                break;
            case INCLUDE:
                request.getRequestDispatcher(commandContent.getNextPage()).include(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(commandContent.getNextPage());
                break;
        }
    }

    private CommandContent chooseCommand(RequestContent requestContent) throws CommandException {
        String commandName = requestContent.getParameter("command");
        CommandEnum commandEnumName = CommandEnum.valueOf(commandName.toUpperCase());
        Command command = commandEnumName.getValue();

        return command.execute(requestContent);
    }

    private String chooseUploadDir(String commandName) {
        String uploadDir;
        switch (commandName) {
            case "serviceUpdate":
                uploadDir = "resource/img/service";
                break;
            default:
                uploadDir = "resource/uploads";
                break;
        }
        return uploadDir;
    }
}
