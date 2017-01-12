package com.ck.spring.db;

import java.io.IOException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月5日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = false)
@Order(3)
public class DataPersistentConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataPersistentConfig.class);

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        LOG.info("load sql sesion factory");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:mybatis/mapper/*Mapper.xml"));
        bean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean(name = { "transactionManager", "openplfm" })
    public DataSourceTransactionManager dataSourceTransactionManager() {
        LOG.info("init openplfm transaction");
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
