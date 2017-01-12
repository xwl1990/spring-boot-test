package com.ck.cst;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2016年12月31日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public interface CommonCst {

    /**
     * 配置文件路径及名称
     */
    String PROPERTIES_PATH_CONFIG = "/config.properties";
    String PROPERTIES_PATH_SOCKET = "/socket.properties";
    String PROPERTIES_PATH_CODIS = "/redis.properties";

    /**
     * UTF-8
     */
    String CHARSET_UTF8 = "1";
    String CHARSET_UTF_8_NAME = "UTF-8";

    /**
     * GBK
     */
    String CHARSET_GBK = "2";
    String CHARSET_GBK_NAME = "GBK";

    /**
     * GB2312
     */
    String CHARSET_GB2312 = "3";
    String CHARSET_GB2312_NAME = "GB2312";

    /**
     * 事务ID
     */
    String TRANSACTION_ID = "transactionId";

    /**
     * response status ok.
     */
    String RESPONSE_OK = "1";

    /**
     * response status FAILD.
     */
    String RESPONSE_FAILD = "0";

    /**
     * [.
     */
    String LEFT_BRACKET = "[";

    /**
     * ].
     */
    String RIGHT_BRACKET = "]";

    /**
     * _.
     */
    String UNDERLINE = "_";

    /**
     * |.
     */
    String VERTICAL_LINE = "|";

    /**
     * ,.
     */
    String COMMON = ",";
    /**
     * ##
     */
    String POUND = "##";

    /**
     * "/".
     */
    String SLASH = "/";

    /**
     * 空值
     */
    String STRING_NULL_VALUE = "null";
    /**
     * empty string
     */
    String STRING_EMPTY_VALUE = "";

}
