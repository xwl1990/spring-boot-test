package com.ck;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ck.util.MapperUtils;
import com.ck.util.OkHttpUtils;
import com.ck.util.SignatureUtils;
import com.ck.util.WeixinXmlUtils;

public class WechatPayTest {

    

    /**
     * 微信扫描支付-报文组装
     * 
     * @param wechatConfig
     *            前置机
     * @param paymentEntity
     *            支付信息
     * @return
     * @throws Exception 
     */
    public static SortedMap<String, String> initNativePayData() throws Exception {
        HashMap<String, String> mapInit = new HashMap<String, String>();
        SortedMap<String, String> map = new TreeMap<String, String>(mapInit);
        // 交易秘钥
        String merchantKey = String.valueOf("2323");
        // 数据组装转换
        map.put("appid", "2323");
        map.put("mch_id", "343");
        //map.put("sub_mch_id", merchantSubNo);
        map.put("out_trade_no", "T"+(new Date().getTime()));
        map.put("body", "定");
        map.put("attach", "attach");
         
        map.put("device_info", "WEB");
        map.put("total_fee", "10000");
        map.put("spbill_create_ip", "192.168.2.0");
        map.put("trade_type", "NATIVE");
        map.put("notify_url", "http://www.abcdfef.com");
        map.put("nonce_str", String.valueOf(new Date().getTime()));

        // 签名
        TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
        
        StringBuffer sb = new StringBuffer();
        String key = treeMap.firstKey();
        sb.append(key).append("=").append(treeMap.get(key));
        while ((key = treeMap.higherKey(key)) != null) {
            if (treeMap.get(key) != null) {
                sb.append("&").append(key).append("=").append(treeMap.get(key));
            }
        }
        //String sign = SignatureUtils.sign(sb.toString(), SignatureUtils.SIGNATURE_MD5, "&key=" + merchantKey);

       // Map<String, Object> map = signAndConvertToMap(treeMap, merchantKey);
        String data = WeixinXmlUtils.convertMapToXML(signAndConvertToMap(treeMap, merchantKey));
        System.out.println("request:\n"+data+"\n");
        data = OkHttpUtils.httpClientJsonPostReturnAsString("https://api.mch.weixin.qq.com/pay/unifiedorder", data, 10);
        System.out.println("response:\n"+data);
        return map;
    }
    
    public static Map<String, Object> signAndConvertToMap(Object object, String signKey) {
        Map<String, Object> beanMap = MapperUtils.beanToMap(object);

        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(beanMap);
        if (treeMap.containsKey("sign")) {
            treeMap.remove("sign");
        }
        if (treeMap.containsKey("class")) {
            treeMap.remove("class");
        }
        if (treeMap.containsKey("appCode")) {
            treeMap.remove("appCode");
        }
        if (treeMap.containsKey("mchtNo")) {
            treeMap.remove("mchtNo");
        }
        StringBuffer sb = new StringBuffer();
        String key = treeMap.firstKey();
        sb.append(key).append("=").append(treeMap.get(key));
        while ((key = treeMap.higherKey(key)) != null) {
            if (treeMap.get(key) != null) {
                sb.append("&").append(key).append("=").append(treeMap.get(key));
            }
        }
        
        String sign = SignatureUtils.sign(sb.toString(), SignatureUtils.SIGNATURE_MD5, "&key=" + signKey);
        System.out.println("sign:\n"+sign+"\n");
        treeMap.put("sign", sign);
        return treeMap;
    }
    
    public static void main(String[] args) throws Exception {
       // WechatPayTest t = new WechatPayTest();
        //t.initNativePayData();
    }
    
    public static SortedMap<String, String> initNativePayData2() throws Exception {
        HashMap<String, String> mapInit = new HashMap<String, String>();
        SortedMap<String, String> map = new TreeMap<String, String>(mapInit);
        String merchantKey = String.valueOf("2323");

        map.put("appid", "2323");
        map.put("mch_id", "2323");
        //map.put("sub_mch_id", merchantSubNo);
        map.put("out_trade_no", "T"+(new Date().getTime()));
        map.put("body", "定");
        map.put("attach", "attach");
         
        map.put("device_info", "WEB");
        map.put("total_fee", "10000");
        map.put("spbill_create_ip", "192.168.2.0");
        map.put("trade_type", "NATIVE");
        map.put("notify_url", "http://www.abcdfef.com");
        map.put("nonce_str", String.valueOf(new Date().getTime()));

        // 签名
        TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
        
        StringBuffer sb = new StringBuffer();
        String key = treeMap.firstKey();
        sb.append(key).append("=").append(treeMap.get(key));
        while ((key = treeMap.higherKey(key)) != null) {
            if (treeMap.get(key) != null) {
                sb.append("&").append(key).append("=").append(treeMap.get(key));
            }
        }
        //String sign = SignatureUtils.sign(sb.toString(), SignatureUtils.SIGNATURE_MD5, "&key=" + merchantKey);

       // Map<String, Object> map = signAndConvertToMap(treeMap, merchantKey);
        String data = WeixinXmlUtils.convertMapToXML(signAndConvertToMap(treeMap, merchantKey));
        System.out.println("request:\n"+data+"\n");
        data = OkHttpUtils.httpClientJsonPostReturnAsString("https://api.mch.weixin.qq.com/pay/unifiedorder", data, 10);
        System.out.println("response:\n"+data);
        return map;
    }

}

	