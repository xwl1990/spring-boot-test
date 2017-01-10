package com.ck.model;

import com.alibaba.fastjson.JSONObject;

public class WeChatPayOrder {

    private String appId;// 公众号id 。微信公众号支付特有
    private String openid;// 用户标识。微信公众号支付特有
    private String mchId;
    private String deviceInfo;// 终端设备号(门店号或收银设备ID)。微信特有 网页 WEB
    private String nonceStr;// 随机字符串 。微信公众号支付特有
    private String body;// 订单名称
    private String sign;// 签名 。微信公众号支付特有
    private String signType="MD5";// 签名方式 。微信公众号支付特有
    private JSONObject detail;
    private String outTradeNo;// 户订单号
    private String totalFee;// 商品标价
    private String spbillCreateIp;// 二维码图片
    private String notifyUrl;// 回调通知地址
    private String tradeType="APP";// 交易类型JSAPI，NATIVE，APP
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getDeviceInfo() {
        return deviceInfo;
    }
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
    public JSONObject getDetail() {
        return detail;
    }
    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public String getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }
    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

}

	