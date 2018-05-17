package com.tsarova.salon.servlet;



import com.tsarova.salon.command.Command;
import com.tsarova.salon.command.CommandEnum;
import com.tsarova.salon.content.CommandContent;
import com.tsarova.salon.content.RequestContent;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.exception.CommandException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.FeedbackRepository;
import com.tsarova.salon.util.Encrypting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import static com.tsarova.salon.content.CommandContent.ResponseType.FORWARD;
import static com.tsarova.salon.content.CommandContent.ResponseType.INCLUDE;
import static com.tsarova.salon.content.CommandContent.ResponseType.REDIRECT;
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5 * 5)
@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    private CommandContent chooseCommand(RequestContent requestContent) throws CommandException {
        String commandName = requestContent.getParameter("command");
        CommandEnum commandEnumName = CommandEnum.valueOf(commandName.toUpperCase());
        Command command = commandEnumName.getValue();

        return command.execute(requestContent);
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

                if (commandContent.getResponseType() == FORWARD) {
                    getServletContext().getRequestDispatcher(commandContent.getNextPage()).forward(request, response);
                } else if (commandContent.getResponseType() == REDIRECT) {
                    response.sendRedirect(commandContent.getNextPage());
                } else if (commandContent.getResponseType() == INCLUDE) {
                    request.getRequestDispatcher(commandContent.getNextPage()).include(request, response);
                }//switch default
            } catch (CommandException e) {
                logger.catching(Level.ERROR, e);
                getServletContext().getRequestDispatcher("/jsp/error/500.jsp").forward(request, response);
            }

    }
}

//<ctg:info-time/>

