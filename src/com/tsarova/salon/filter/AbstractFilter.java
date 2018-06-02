package com.tsarova.salon.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * @author Veronika Tsarova
 */
public abstract class AbstractFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void destroy() {
        filterConfig = null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}