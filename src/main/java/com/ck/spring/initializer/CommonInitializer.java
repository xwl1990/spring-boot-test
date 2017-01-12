package com.ck.spring.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月5日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Order(1)
public class CommonInitializer implements WebApplicationInitializer {

    public CommonInitializer() {
        System.out.println("CommonInitializer");
    }

    private static final Logger LOG = LoggerFactory.getLogger(CommonInitializer.class);

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.setInitParameter("logbackConfigLocation", "logback.xml");
        sc.addListener(LogbackConfigListener.class);
        LOG.info("Dynamic loading web.xml end.");
    }

}
