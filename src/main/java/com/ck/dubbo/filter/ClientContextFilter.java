
package com.ck.dubbo.filter;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.MDC;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.ck.dubbo.cst.DubboCst;

/**
 * Description: 设置服务端MDC相关参数，如：transactionId
 *
 * @author: xieweili
 * @since: 2017年1月12日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@Activate(group = Constants.CONSUMER, value = DubboCst.CONSUMER_CONTEXT_KEY)
public class ClientContextFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        
        RpcContext.getContext().setInvoker(invoker).setInvocation(invocation).setLocalAddress(NetUtils.getLocalHost(), 0).setRemoteAddress(invoker.getUrl().getHost(), invoker.getUrl().getPort());

        // 根据客户端访问日志MDC参数，设置服务端日志参数，请求服务端时，会带上相应参数
        Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        if (mdcMap != null && !mdcMap.isEmpty()) {
            for (Entry<String, String> entry : mdcMap.entrySet()) {
                RpcContext.getContext().setAttachment(entry.getKey(), entry.getValue());
            }
        }

        if (invocation instanceof RpcInvocation) {
            ((RpcInvocation) invocation).setInvoker(invoker);
        }
        try {
            return invoker.invoke(invocation);
        } finally {
            RpcContext.getContext().clearAttachments();
        }
    }

}
