package com.ck.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ck.common.GenericRequestDto;
import com.ck.cst.WechatPayCst;
import com.ck.model.WeixinMicroReverseRespParam;
import com.ck.model.WeixinUnifiedOrderRespParam;
import com.ck.svc.UserSvc;
import com.ck.util.WechatPayUtil;

@Controller
public class PayController extends BascController{
    
    private static final Logger LOG = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private UserSvc userSvc;
    
    @RequestMapping(value = "/html/{page}", method = RequestMethod.GET)
    public ModelAndView index1(HttpServletRequest req, @PathVariable(value="page") String page) {
        ModelAndView m = new ModelAndView(page);
        LOG.info(getSession(req).getId());
        return m;
    }
    
    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = "application/json")
    public JSONObject pay(HttpServletRequest req, @RequestBody GenericRequestDto reqDto) throws Exception {
        Double money = Double.valueOf(reqDto.getStringData("money"));
        JSONObject json = new JSONObject();
        WeixinUnifiedOrderRespParam p = new WeixinUnifiedOrderRespParam();
        p.setAppid(WechatPayCst.APP_ID);
        p.setBody("1");
        p.setMch_id(WechatPayCst.MCHT_ID);
        p.setSign_type("MD5");
        p.setNotify_url("http://www.baiduc.om");
        p.setTrade_type("NATIVE");//
        p.setTotal_fee(Math.round(money * 100)+"");
        WeixinMicroReverseRespParam r = WechatPayUtil.unifiedorder(p);
        json.put("data", r);
        LOG.info(json.toJSONString());
        return json;
    }

}
