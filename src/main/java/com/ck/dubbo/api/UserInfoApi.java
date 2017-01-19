package com.ck.dubbo.api;

import com.alibaba.fastjson.JSONObject;

public interface UserInfoApi {

    public JSONObject getUser(String userId); 
    
}

