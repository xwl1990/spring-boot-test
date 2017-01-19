package com.ck.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.ck.util.MapperUtils;

public class TestAlipayMain {

    public static void main(String[] args) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2015071600173382", "your private_key", "json", "GBK", "alipay_public_key", "RSA2");
        AlipayTradePrecreateContentBuilder b = new AlipayTradePrecreateContentBuilder();
        b.setSubject("支付宝收款");
        b.setTotalAmount("100.5");
        b.setOutTradeNo("a123123131");
        b.setBody("商品描述");
        b.setStoreId("test_store_id");
        b.setOperatorId("test_operator_id");
        b.setTimeExpress("15m");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(MapperUtils.toJson(b).toString());
        
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            response.getOutTradeNo();
            response.getQrCode();
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }

}
