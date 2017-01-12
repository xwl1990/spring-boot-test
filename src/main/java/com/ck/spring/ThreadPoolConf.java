package com.ck.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月5日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@PropertySource("classpath:properties/thread_pool.properties")
@Component
public class ThreadPoolConf {

    // 业务线程初始化值
    @Value("${thread.pool.busCorePoolSize}")
    private int busCorePoolSize;// =10;

    // 业务线程最大值
    @Value("${thread.pool.busMaxPoolSize}")
    private int busMaxPoolSize;// =30;

    // 业务线程队列初始值
    @Value("${thread.pool.busQueueCapacity}")
    private int busQueueCapacity;// =30;

    // 异步线程空闲时间
    @Value("${thread.pool.busKeepAliveSeconds}")
    private int busKeepAliveSeconds;// =60;

    // 异步线程名称前缀
    @Value("${thread.pool.busThreadNamePrefix}")
    private String busThreadNamePrefix;// ="TaskExecutor-";

    public int getBusCorePoolSize() {
        return busCorePoolSize;
    }

    public void setBusCorePoolSize(int busCorePoolSize) {
        this.busCorePoolSize = busCorePoolSize;
    }

    public int getBusMaxPoolSize() {
        return busMaxPoolSize;
    }

    public void setBusMaxPoolSize(int busMaxPoolSize) {
        this.busMaxPoolSize = busMaxPoolSize;
    }

    public int getBusQueueCapacity() {
        return busQueueCapacity;
    }

    public void setBusQueueCapacity(int busQueueCapacity) {
        this.busQueueCapacity = busQueueCapacity;
    }

    public int getBusKeepAliveSeconds() {
        return busKeepAliveSeconds;
    }

    public void setBusKeepAliveSeconds(int busKeepAliveSeconds) {
        this.busKeepAliveSeconds = busKeepAliveSeconds;
    }

    public String getBusThreadNamePrefix() {
        return busThreadNamePrefix;
    }

    public void setBusThreadNamePrefix(String busThreadNamePrefix) {
        this.busThreadNamePrefix = busThreadNamePrefix;
    }

}
