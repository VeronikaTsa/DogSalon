package com.tsarova.salon.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Veronika Tsarova
 */
public class DecodeTag extends BodyTagSupport {
    private static Logger logger = LogManager.getLogger();

    public int doAfterBody() throws JspTagException {
        BodyContent bodyContent = getBodyContent();
        String bodyString = bodyContent.getString();
        String decodedBody = null;

        try {
            decodedBody = URLDecoder.decode(bodyString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.catching(Level.ERROR, e);
        }

        bodyContent.clearBody();
        JspWriter out = bodyContent.getEnclosingWriter();

        try {
            out.write(decodedBody != null ? decodedBody : "");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }
}