package com.tsarova.salon.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Veronika Tsarova
 */
public class RequestContent {

    private String contextPath;
    private String requestURI;
    private String method;
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private boolean sessionInvalidateFlag;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        contextPath = request.getContextPath();
        requestURI = request.getRequestURI();
        method = request.getMethod();
        extractAttributes(request);
        extractParameters(request);
        extractSessionAttributes(request);
        sessionInvalidateFlag = false;
    }

    public void insertValues(HttpServletRequest request) {
        insertAttributes(request);
        insertSessionAttributes(request);
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getMethod() {
        return method;
    }

    public Object getAttribute(String key) {
        return requestAttributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public String[] getParameters(String key) {
        return requestParameters.get(key);
    }

    public String getParameter(String key) {
        String[] params = requestParameters.get(key);
        if (params == null || params.length == 0) {
            return null;
        } else {
            return params[0];
        }
    }

    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public void setSessionAttribute(String attributeName, Object attributeValue) {
        sessionAttributes.put(attributeName, attributeValue);
    }

    public void setSessionInvalidateFlag(boolean value) {
        this.sessionInvalidateFlag = value;
    }

    private void extractAttributes(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }
    }

    private void extractParameters(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            requestParameters.put(parameterName, request.getParameterValues(parameterName));
        }
    }

    private void extractSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Enumeration<String> sessionAttributeNames = session.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String sessionAttributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(sessionAttributeName, session.getAttribute(sessionAttributeName));
            }
        }
    }

    private void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> pair : requestAttributes.entrySet()) {
            request.setAttribute(pair.getKey(), pair.getValue());
        }
    }

    private void insertSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            for (Map.Entry<String, Object> pair : sessionAttributes.entrySet()) {
                session.setAttribute(pair.getKey(), pair.getValue());
            }
            if (sessionInvalidateFlag) {
                session.invalidate();
            }
        }
    }

    public void removeAttribute(String key) {
        if (requestAttributes.containsKey(key)) {
            requestAttributes.put(key, null);
        }
    }

    public void removeSessionAttribute(String key) {
        if (sessionAttributes.containsKey(key)) {
            sessionAttributes.put(key, null);
        }
    }
}