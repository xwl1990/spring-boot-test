package com.ck.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ck.cst.CommonCst;
import com.ck.util.RequestUtils;

/**
 * Description: 日志过滤
 *
 * @author: xieweili
 * @since: 2017年1月12日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class MDCFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(MDCFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("MDCFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();

        // 过滤资源文件，druid等信息
        if (!RequestUtils.isStaticResources(url)) {
            String transactionId = StringUtils.EMPTY;
            MDC.remove(CommonCst.TRANSACTION_ID);
            // 从http参数中获取transactionID
            String transFromPara = req.getParameter(CommonCst.TRANSACTION_ID);
            // 从header中获取transactionID
            String transFromHeader = req.getHeader(CommonCst.TRANSACTION_ID);
            String serviceId = RequestUtils.getRequestPathKey(req);
            if (StringUtils.isBlank(transFromPara)) {
                if (StringUtils.isBlank(transFromHeader)) {
                    transactionId = StringUtils.join(req.getSession().getId(), CommonCst.UNDERLINE, RequestUtils.getMobileDeviceId(req), CommonCst.UNDERLINE, serviceId);
                } else {
                    transactionId = transFromHeader;
                }
            } else {
                transactionId = transFromPara;
            }

            MDC.put(CommonCst.TRANSACTION_ID, transactionId);
            LOG.info("transaction from request parameter is " + transFromPara + ",transactionId from header is " + transFromHeader + "--sessionid:" + req.getSession().getId() + ",requrl="
                    + req.getPathInfo());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.remove(CommonCst.TRANSACTION_ID);
    }
   
}
