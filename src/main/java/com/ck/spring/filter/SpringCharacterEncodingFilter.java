package com.ck.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月6日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class SpringCharacterEncodingFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SpringCharacterEncodingFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("servletFilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("GBK");
        filter.setForceEncoding(true);
        filter.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        LOG.info("servletFilter destory...");
    }


}

	