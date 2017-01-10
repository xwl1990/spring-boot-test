package com.ck.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring文件加载，dubbo配置
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月10日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@PropertySource(value = "classpath*:properties/config.properties", ignoreResourceNotFound = true)
@ImportResource({"classpath*:dubbo/applicationContext-dubbo-*.xml" })
public class SpringConfig {

    

}
