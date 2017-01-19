package com.ck.spring.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.StringHttpMessageConverter;

import com.ck.cst.CommonCst;
import com.ck.dubbo.cst.DubboCst;

/**
 * Description: http 请求与响应打印接口日志，输出是@ResposeBody String类型时
 *
 * @author: xieweili
 * @since: 2017年1月18日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class LogStringHttpMessageConverter extends StringHttpMessageConverter {

    private final Logger logger = LoggerFactory.getLogger(DubboCst.PROVIDE_INTERFACE_LOG_KEY);

    public final static Charset UTF8 = Charset.forName(CommonCst.CHARSET_UTF_8_NAME);

    public LogStringHttpMessageConverter() {
        super(UTF8);
    }

    public LogStringHttpMessageConverter(Charset defaultCharset) {
        super(defaultCharset);
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException {
        String requestStr = super.readInternal(clazz, inputMessage);
        logger.info("[message convert request] - " + requestStr);
        return requestStr;
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        super.writeInternal(s, outputMessage);
        logger.info("[message convert response] - " + s);
    }

}
