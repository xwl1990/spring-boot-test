package com.ck.spring;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月5日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)
@PropertySource(value = "classpath:properties/config.properties", ignoreResourceNotFound = true)
@ImportResource({ "classpath*:dubbo/*.xml" })
public class AppConfig {
    
    @Autowired
    private ThreadPoolConf threadPoolConf;

    @Bean(name = "springUtils")
    public SpringUtils getSpringUtils() {
        System.out.println("getSpringUtils");
        return new SpringUtils();
    }
    
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
    
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(threadPoolConf.getBusCorePoolSize());
        taskExecutor.setMaxPoolSize(threadPoolConf.getBusMaxPoolSize());
        taskExecutor.setQueueCapacity(threadPoolConf.getBusQueueCapacity());
        taskExecutor.setKeepAliveSeconds(threadPoolConf.getBusKeepAliveSeconds());
        taskExecutor.setThreadNamePrefix(threadPoolConf.getBusThreadNamePrefix());
        taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler());
        return taskExecutor;
    }
    
    @Bean
    public RejectedExecutionHandler rejectedExecutionHandler() {
        return new ThreadPoolExecutor.DiscardPolicy();
    }

}
