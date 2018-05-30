package com.tsarova.salon.servlet;

import com.tsarova.salon.command.Command;
import com.tsarova.salon.command.CommandEnum;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.tsarova.salon.content.CommandContent.ResponseType.FORWARD;
import static com.tsarova.salon.content.CommandContent.ResponseType.INCLUDE;
import static com.tsarova.salon.content.CommandContent.ResponseType.REDIRECT;

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
        System.out.println("serviceId: " + request.getParameter("serviceId"));

        if ("serviceUpdate".equals(request.getParameter("command"))) {
            final String UPLOAD_DIR = "resource/img/service";
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            requestContent.extractValues(request);
            System.out.println(request.getParameter("pictureName"));
            requestContent.setAttribute("pictureName", request.getParameter("pictureName"));

            try {
                for (Part part : request.getParts()) {
                    if (part.getSubmittedFileName() != null) {
                        part.write(uploadFilePath + File.separator + part.getSubmittedFileName());
                        requestContent.setAttribute("pictureName", part.getSubmittedFileName());
                        }
                }
            } catch (IOException | ServletException e) {
                logger.catching(Level.ERROR, e);
                //??????????????????????
            }
        }
        CommandContent commandContent = null;
        try {
            commandContent = chooseCommand(requestContent);
        } catch (CommandException e) {
            logger.catching(Level.ERROR, e);
        }
        requestContent.insertValues(request);
        if (commandContent.getResponseType() == FORWARD) {
            getServletContext().getRequestDispatcher(commandContent.getNextPage()).forward(request, response);
        } else if (commandContent.getResponseType() == REDIRECT) {
            response.sendRedirect(commandContent.getNextPage());
        } else if (commandContent.getResponseType() == INCLUDE) {
            request.getRequestDispatcher(commandContent.getNextPage()).include(request, response);
        }//switch default
    }

    private CommandContent chooseCommand(RequestContent requestContent) throws CommandException {
        String commandName = requestContent.getParameter("command");
        CommandEnum commandEnumName = CommandEnum.valueOf(commandName.toUpperCase());
        Command command = commandEnumName.getValue();

        return command.execute(requestContent);
    }
}
