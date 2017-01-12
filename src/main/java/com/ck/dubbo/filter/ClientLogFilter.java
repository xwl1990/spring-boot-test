package com.ck.dubbo.filter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.alibaba.fastjson.JSON;
import com.ck.dubbo.cst.DubboCst;
import com.ck.dubbo.util.DubboUtil;

/**
 * Description: 客户端接口日志过滤器
 *
 * @author: xieweili
 * @since: 2017年1月12日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Activate(group = Constants.CONSUMER, value = DubboCst.CONSUMER_LOG_KEY)
public class ClientLogFilter implements Filter{
    
    private static final Logger logger = LoggerFactory.getLogger(ClientLogFilter.class);
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger(DubboCst.CONSUMER_LOGGER_ACCESS);
    private static final String PROVIDER = "provider";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long s = System.currentTimeMillis();
        Result result = null;
        try {
            result = invoker.invoke(invocation);
            long e = System.currentTimeMillis();
            String service = invoker.getUrl().getServiceInterface();
            
            String serviceKey = service.substring(service.lastIndexOf(".") + 1) + "_" + RpcUtils.getMethodName(invocation);
            Map<String, String> accessMap = DubboUtil.getAccessMap(result, serviceKey, s, e);
            accessMap.put(PROVIDER, RpcContext.getContext().getRemoteAddressString());
            ACCESS_LOG.info(JSON.toJSONString(accessMap));
        } catch (Exception e) {
            logger.error("dubbo interface invocation error.", e);
        }
        return result;
    }
}

	