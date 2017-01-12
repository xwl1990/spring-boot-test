package com.ck.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * Description: dubbo配置
 *
 * @author: xieweili
 * @since: 2017年1月11日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@PropertySource("classpath:properties/dubbo.properties")
@Component
public class DubboConfig {

    @Value("${dubbo.application.name}")
    private String applicationName;
    @Value("${dubbo.application.owner}")
    private String owner;
    @Value("${dubbo.registry.address}")
    private String address;
    @Value("${dubbo.registry.register}")
    private String register;
    @Value("${dubbo.port}")
    private String port;
    @Value("${dubbo.rest.port}")
    private String restPort;
    @Value("${dubbo.rest.server}")
    private String restServer;
    @Value("${dubbo.consumer.timeout}")
    private String consumerTimeout;
    @Value("${dubbo.provider.timeout}")
    private String providerTimeout;
    @Value("${dubbo.provider.retries}")
    private String providerRetries;
    @Value("${dubbo.consumer.retries}")
    private String consumerRetries;
    @Value("${dubbo.threads}")
    private String threads;
    @Value("${dubbo.rest.path}")
    private String restPath;
    @Value("${dubbo.api.version}")
    private String dubboApiVersion;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRestPort() {
        return restPort;
    }

    public void setRestPort(String restPort) {
        this.restPort = restPort;
    }

    public String getRestServer() {
        return restServer;
    }

    public void setRestServer(String restServer) {
        this.restServer = restServer;
    }

    public String getConsumerTimeout() {
        return consumerTimeout;
    }

    public void setConsumerTimeout(String consumerTimeout) {
        this.consumerTimeout = consumerTimeout;
    }

    public String getConsumerRetries() {
        return consumerRetries;
    }

    public void setConsumerRetries(String consumerRetries) {
        this.consumerRetries = consumerRetries;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getRestPath() {
        return restPath;
    }

    public void setRestPath(String restPath) {
        this.restPath = restPath;
    }

    public String getProviderTimeout() {
        return providerTimeout;
    }

    public void setProviderTimeout(String providerTimeout) {
        this.providerTimeout = providerTimeout;
    }

    public String getProviderRetries() {
        return providerRetries;
    }

    public void setProviderRetries(String providerRetries) {
        this.providerRetries = providerRetries;
    }

    public String getDubboApiVersion() {
        return dubboApiVersion;
    }

    public void setDubboApiVersion(String dubboApiVersion) {
        this.dubboApiVersion = dubboApiVersion;
    }

}
