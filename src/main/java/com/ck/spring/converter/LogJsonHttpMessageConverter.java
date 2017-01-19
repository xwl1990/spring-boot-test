package com.ck.spring.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ck.cst.CommonCst;
import com.ck.dubbo.cst.DubboCst;
import com.ck.util.MapperUtils;

/**
 * Description: http 请求与响应打印接口日志，输出是@ResposeBody Object类型时
 *
 * @author: xieweili
 * @since: 2017年1月18日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class LogJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private final Logger logger = LoggerFactory.getLogger(DubboCst.PROVIDE_INTERFACE_LOG_KEY);

    public final static Charset UTF8 = Charset.forName(CommonCst.CHARSET_UTF_8_NAME);

    private Charset charset = UTF8;

    private SerializerFeature[] features = new SerializerFeature[0];

    private SerializeFilter[] outFilters;

    private SerializeFilter[] inFilters;

    public LogJsonHttpMessageConverter() {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }

    public SerializeFilter[] getOutFilters() {
        return outFilters;
    }

    public void setOutFilters(SerializeFilter[] outFilters) {
        this.outFilters = outFilters;
    }

    public SerializeFilter[] getInFilters() {
        return inFilters;
    }

    public void setInFilters(SerializeFilter[] inFilters) {
        this.inFilters = inFilters;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        Object obj = JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
        String text = CommonCst.STRING_EMPTY_VALUE;
        if ( obj != null) {
            text = MapperUtils.toJSONString(obj, inFilters, features);
        }
        logger.info("[request] - " + text);
        
        return obj;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = MapperUtils.toJSONString(obj, outFilters, features);
        logger.info("[response] - " + text);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
    }

}
