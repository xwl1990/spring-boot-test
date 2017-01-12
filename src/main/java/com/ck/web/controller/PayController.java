package com.ck.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ck.svc.UserSvc;

@Controller
public class PayController extends BascController{
    
    private static final Logger LOG = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private UserSvc userSvc;
    
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public ModelAndView index1(HttpServletRequest req) {
        ModelAndView m = new ModelAndView("pay");
        
        LOG.info(getSession(req).getId());
        m.addObject("test", "asdfafdasdfas");
        return m;
    }

}
