package com.ck.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 *  异步处理
 * The class ExceptionController.
 *
 * Description: 
 *
 * @author: xieweili
 * @since: 2016年12月29日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
@ControllerAdvice
public class ExceptionController{

	protected static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public JSONObject handleException(Throwable ex) {
		LOG.error("44444error: ", ex);
		JSONObject resp = new JSONObject();
		resp.put("aaaa", "Throwable");
		return resp;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public JSONObject handleHttpMessageNotReadableException(Throwable ex) {
		LOG.error("33333error: ", ex);
        JSONObject resp = new JSONObject();
        resp.put("aaaa", "handleHttpMessageNotReadableException");
        return resp;
	}
	
	@ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JSONObject handleNullPointerException(Throwable ex) {
        LOG.error("222222error: ", ex);
        JSONObject resp = new JSONObject();
        resp.put("aaaa", "NullPointerException");
        return resp;
    }
	
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
    @ResponseBody
    public JSONObject handleStringIndexOutOfBoundsException(Throwable ex) {
        LOG.error("111111error: ", ex);
        JSONObject resp = new JSONObject();
        resp.put("aaaa", "StringIndexOutOfBoundsException");
        return resp;
    }
	
}
