package com.tsarova.salon.filter;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.resource.page.PageResourceManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Veronika Tsarova
 */
@WebFilter( urlPatterns = { "/jsp/admin/*" })
public class AdminFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null || (session.getAttribute("user") != null &&
                !"administrator".equals(((User) session.getAttribute("user")).getRole().getValue()))) {
            httpResponse.sendRedirect(PageResourceManager.getInstance().getValue("jsp.index"));
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}