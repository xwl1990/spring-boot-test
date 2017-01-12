package com.ck.spring.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ck.spring.AppConfig;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月6日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Order(3)
public class WebCommonInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public WebCommonInitializer() {
        System.out.println("WebCommonInitializer");
    }

    /*
     * 应用上下文，除web部分
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    /*
     * web上下文
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {};
    }

    /*
     * DispatcherServlet的映射路径
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /*
     * 定义支持http异步请求处理
     */
    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setAsyncSupported(true);
    }

    /*
     * 注册过滤器，映射路径与DispatcherServlet一致，
     * 路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }

}
