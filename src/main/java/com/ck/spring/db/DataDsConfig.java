package com.ck.spring.db;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ck.util.MapperUtils;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月5日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Configuration
@Order(2)
public class DataDsConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataDsConfig.class);

    @Autowired
    private DataConfig dataConfig;

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        LOG.info("load datasource start.");

        try {
            Map<String, String> dsMap = MapperUtils.beanToMap(dataConfig);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(dsMap);
            return dataSource;
        } catch (Exception e) {
            LOG.error("load database error", e);
        }

        return null;
    }

}
