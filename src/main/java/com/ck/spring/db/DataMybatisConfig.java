package com.ck.spring.db;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月5日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@Order(3)
public class DataMybatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataMybatisConfig.class);

    /**
     * 扫描mybatis配置文件
     */
    @Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer mapperConfigurer() {
        logger.info("mybatis configurer load.");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.ck.dao.mapper.*");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

}
