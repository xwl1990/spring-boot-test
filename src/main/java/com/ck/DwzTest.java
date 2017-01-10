package com.ck;

import java.util.HashMap;
import java.util.Map;

import com.ck.cst.SysCst;
import com.ck.util.MapperUtils;
import com.ck.util.OkHttpUtils;

public class DwzTest {

    public static void main(String[] args) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("url", "http://172.162.35.62/blog/2301571");
        System.out.println("call 581 req:{}"+ MapperUtils.toJson(paramMap));
        Map<String, Object> retMap = OkHttpUtils.httpClientPostFormReturnMap(SysCst.DWZ_581_URL, paramMap, 5);
        System.out.println("call 581 resp:{}"+ MapperUtils.toJson(retMap));
    	
    }

}

	