package com.ck.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BascController {

    public HttpSession getSession(HttpServletRequest req){
        return req.getSession(true);
    }
}
