package com.ck.dubbo.filter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ck.cst.CommonCst;
import com.ck.cst.SysCst;
import com.ck.dubbo.cst.DubboCst;
import com.ck.dubbo.cst.SpecificSymbolCst;
import com.ck.dubbo.util.DubboUtil;
import com.ck.dubbo.util.IdWorker;
import com.ck.dubbo.util.RpcContextUtils;
import com.ck.util.MapperUtils;

/**
 * Description: 服务端接口日志过滤器
 *
 * @author: xieweili
 * @since: 2017年1月11日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Activate(group = Constants.PROVIDER, value = DubboCst.PROVIDE_INTERFACE_LOG_KEY)
public class InterfaceLogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SysCst.COM_INTERFACE);

    private static final ConcurrentMap<String, Logger> LOGGERS = new ConcurrentHashMap<String, Logger>();

    private static final String INTERFACE_LOG_KEY = "dubbo.interfaceLog";
    
    private static final IdWorker idWorker = new IdWorker(ThreadLocalRandom.current().nextInt(0, 15));

    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        Result result = null;
        try {
            removeAllMDC();
            setCurrentLocalMDC(inv.getAttachments());
            StringBuilder sn = new StringBuilder();

            sn.append(inv.getMethodName());
            sn.append(" ");
            sn.append("[request] - ");
            Object[] args = inv.getArguments();
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    sn.append(MapperUtils.toJSONString(arg, DubboUtil.getReqExcludeFilters(), SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.SortField));
                }
            }
            String msg = sn.toString();
                LoggerFactory.getLogger(INTERFACE_LOG_KEY + "." + invoker.getInterface().getName()).info(msg);
                logger.info(msg);
            result = invoker.invoke(inv);

                String text = "[response] - " + MapperUtils.toJson(result.getValue());
                logger.info(text);
                getLogger(INTERFACE_LOG_KEY + "." + invoker.getInterface().getName()).info(text);
        } catch (Throwable t) {
            logger.warn("Exception in InterfaceLogFilter of service(" + invoker + " -> " + inv + ")", t);
        }

        return result;
    }

    /**
     * 获取日志输出器
     * 
     * @param key
     *            分类键
     * @return 日志输出器, 后验条件: 不返回null.
     */
    public static Logger getLogger(String key) {
        Logger logger = LOGGERS.get(key);
        if (logger == null) {
            LOGGERS.putIfAbsent(key, LoggerFactory.getLogger(key));
            logger = LOGGERS.get(key);
        }
        return logger;
    }

    /**
     * 清除缓存设置 
     */
    private void removeAllMDC() {
        Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        if (mdcMap != null && !mdcMap.isEmpty()) {
            for (Entry<String, String> entry : mdcMap.entrySet()) {
                MDC.remove(entry.getKey());
            }
        }
    }

    /**
     * 重新设置MDC参数 
     */
    private void setCurrentLocalMDC(Map<String, String> attMap) {
        if (attMap == null || attMap.isEmpty()) {
            return;
        }

        MDC.setContextMap(attMap);
        if (StringUtils.isEmpty(MDC.get(CommonCst.TRANSACTION_ID))) {
            MDC.put(CommonCst.TRANSACTION_ID, RpcContextUtils.getRpcClientAddress() + SpecificSymbolCst.UNDERLINE + String.valueOf(idWorker.nextId()));
        }
    }

}