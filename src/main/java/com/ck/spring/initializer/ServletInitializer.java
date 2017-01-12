package com.ck.spring.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpEncodingProperties;
import org.springframework.boot.context.web.OrderedCharacterEncodingFilter;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

public class ServletInitializer extends SpringBootServletInitializer {

    @Autowired
    private HttpEncodingProperties httpEncodingProperties;

    @Bean
    public OrderedCharacterEncodingFilter characterEncodingFilter() {
        OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.httpEncodingProperties.getCharset().name());
        filter.setForceEncoding(this.httpEncodingProperties.isForce());
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }

}
