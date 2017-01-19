package com.ck.spring;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.ck.spring.converter.LogJsonHttpMessageConverter;
import com.ck.spring.filter.JsonPropertyFilter;

/**
 * Description: Spring mvc servlet相关配置及Json格式
 *
 * @author: xieweili
 * @since: 2017年1月18日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@ComponentScan(basePackages = "com.ck", excludeFilters = @ComponentScan.Filter(value = Service.class, type = FilterType.ANNOTATION))
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final List<MediaType> SUPPORTED_MEDIA_TYPES = Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN);

    private static final Logger LOG = LoggerFactory.getLogger(WebMvcConfig.class);


    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);
        return handlerMapping;
    }

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerAdapter = super.requestMappingHandlerAdapter();
        handlerAdapter.setOrder(1);
        handlerAdapter.setCacheSeconds(0);
        handlerAdapter.getMessageConverters().add(0, new StringHttpMessageConverter());
        handlerAdapter.getMessageConverters().add(1, protobufJsonMessageConverter());
        handlerAdapter.getMessageConverters().add(2, new ResourceHttpMessageConverter());
        return handlerAdapter;
    }

    @Bean
    public JsonPropertyFilter requestJsonPropertyFilter() {
        String excludeStr = "session,moduleId,transactionId";
        if (StringUtils.isEmpty(excludeStr)) {
            return new JsonPropertyFilter();
        } else {
            return new JsonPropertyFilter(excludeStr.split(","));
        }
    }

    @Bean
    public JsonPropertyFilter responseJsonPropertyFilter() {
        String excludeStr = "session,moduleId,transactionId";
        if (StringUtils.isEmpty(excludeStr)) {
            return new JsonPropertyFilter();
        } else {
            return new JsonPropertyFilter(excludeStr.split(","));
        }
    }
     
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("/static/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
    }

    @Bean
    public LogJsonHttpMessageConverter protobufJsonMessageConverter() {
        LogJsonHttpMessageConverter fastConveter = new LogJsonHttpMessageConverter();
        fastConveter.setSupportedMediaTypes(SUPPORTED_MEDIA_TYPES);
        fastConveter.setInFilters(new SerializeFilter[] { requestJsonPropertyFilter() });
        fastConveter.setOutFilters(new SerializeFilter[] { responseJsonPropertyFilter() });
        return fastConveter;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
}