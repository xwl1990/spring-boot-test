package com.ck.model;

/**
 * 统一下单响应参数 Description:
 *
 * @author: xieweili
 * @since: 2017年1月10日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinUnifiedOrderRespParam extends WeixinBaseRespParam {

    /**
     * 交易类型 JSAPI、NATIVE、APP
     */
    private String trade_type;

    /**
     * 微信生成的预支付ID，用于后续接口调用中使用
     */
    private String prepay_id;

    /**
     * trade_type 为NATIVE 是有返回，此参数可直接生成二维码展示出来进行扫码支付
     */
    private String code_url;

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

}
