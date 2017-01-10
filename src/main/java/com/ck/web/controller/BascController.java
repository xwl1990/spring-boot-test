package com.ck.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@SpringBootApplication
@Controller
public abstract class BascController {

    @RequestMapping(value = "/basc", method = RequestMethod.GET)
    public String index(){
        return null;
    }

}
