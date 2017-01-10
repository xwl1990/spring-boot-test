package com.ck.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ck.svc.UserSvc;

@Controller
public class PayController{
    
    private static final Logger LOG = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private UserSvc userSvc;
    
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String index1() {
        LOG.info("pay");
        return "pay";
    }

}
