package com.ck.dubbo.cst;

public interface DubboCst {

    String CONSUMER_CONTEXT_KEY = "ccKey";
    
    String CONSUMER_LOG_KEY = "clientLogFilter";

    String CONSUMER_LOGGER_ACCESS = "dubbo-consumer-access";
    
    String PROVIDE_INTERFACE_LOG_KEY = "serviceLogFilter";
    /**
     * 上下文事务ID
     */
    String TRANSACTION_ID_KEY = "transactionId";

    /**
     * 请求头关键字
     */
    String REQUEST_HEADER = "requestHeader";

    /**
     * 响应码
     */
    String RESULT_CODE = "resultCode";

    /**
     * 错误码
     */
    String ERROR_CODE = "errorCode";

    /**
     * 错误描述
     */
    String ERROR_DESC = "errorDesc";
}
