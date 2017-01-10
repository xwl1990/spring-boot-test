package com.ck.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ck.svc.UserSvc;

@Controller
public class TestRestController extends BascController{
    
    private static final Logger LOG = LoggerFactory.getLogger(TestRestController.class);

    @Autowired
    private UserSvc userSvc;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject index1() {
        JSONObject json = new JSONObject();
        json.put("a", "1");
        json.put("b", "2");
        json.put("c", "3");
        //String.valueOf(json.get("")).charAt(21);
        //userSvc.queryUserList();
        
        json.put("data",userSvc.queryUserList());
        LOG.info(json.toJSONString());
        return json;
    }

}
