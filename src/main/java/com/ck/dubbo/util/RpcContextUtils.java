package com.ck.dubbo.util;

import java.util.Map;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月11日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class RpcContextUtils {

    private static final String APPLICATION = "application";
    
    /**
     * 获取RPC上下文
     * @return
     */
    public static RpcContext getRpcContext() {
        return RpcContext.getContext();
    }
    
    /**
     * 本应用名称
     * @return
     */
    public static String getApplicationName() {
        return getRpcContext().getUrl().getParameter(APPLICATION);
    }

    /**
     * dubbo客户端IP
     * @return
     */
    public static String getRpcClientIp() {
        return getRpcContext().getRemoteHost();
    }

    /**
     * dubbo客户端port
     * @return
     */
    public static String getRpcClientAddress() {
        return getRpcContext().getRemoteAddressString();
    }

    /**
     * 客户端主机名
     * @return
     */
    public static String getClientHostName() {
        return getRpcContext().getRemoteHostName();
    }
    
    /**
     * 获取上下文参数
     * @param key
     * @return
     */
    public static String getAttachment(String key) {
        return getRpcContext().getAttachment(key);
    }
    
    /**
     * 获取所有上下文参数
     * @param key
     * @return
     */
    public static Map<String, String> getAllAttachment() {
        return getRpcContext().getAttachments();
    }
}

	