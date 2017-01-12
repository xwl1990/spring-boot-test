package com.ck.common;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月11日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class GenericRequestHeader implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6397649455306976585L;

    /**
     * 客户端版本
     */
    private String clientVersion;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * 移动设备类型, ios, android
     */
    private String channel;

    /**
     * 移动设备系统版本号
     */
    private String osVersion;

    /**
     * 移动设备ID
     */
    private String imei;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 移动设备型号
     */
    private String model;

    /**
     * 当前会话ID
     */
    private String sessionId;

    /**
     * 产品名称
     */
    private String productName;

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "GenericRequestHeader [clientVersion=" + clientVersion + ", clientType=" + clientType + ", osVersion=" + osVersion + ", imei=" + imei + ", manufacturer=" + manufacturer + ", model="
                + model + "]";
    }

}
