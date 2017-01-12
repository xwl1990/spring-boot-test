package com.ck.util;

import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ck.model.WeixinBaseRespParam;
import com.ck.model.WeixinMicroReverseRespParam;
import com.ck.model.WeixinNativePayModel;
import com.ck.model.WeixinQrTradeCallbackModel;
import com.ck.model.WeixinQueryStatusRespParam;
import com.ck.model.WeixinShortUrlRespParam;
import com.ck.model.WeixinUnifiedOrderRespParam;

/**
 * Description: 解析微信接口返回文本
 *
 * @author: xieweili
 * @since: 2016年12月31日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinXmlUtils {

    /**
     * 将对象转化成XML
     * 
     * @param map
     * @return
     */
    public static String convertMapToXML(Map<String, Object> map) {
        // 得到document对
        Document document = DocumentHelper.createDocument();
        // 添加根节点
        Element root = document.addElement("xml");

        Element e = null;
        for (Entry<String, Object> item : map.entrySet()) {
            if (item.getValue() != null) {
                e = root.addElement(item.getKey());
                e.setText(String.valueOf(item.getValue()));
            }
        }

        return root.asXML();
    }

    public static WeixinNativePayModel parseNativePayModel(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinNativePayModel param = new WeixinNativePayModel();

        param.setAppid(p.getValue("xml.appid"));
        param.setIs_subscribe(p.getValue("xml.is_subscribe"));
        param.setMch_id(p.getValue("xml.mch_id"));
        param.setNonce_str(p.getValue("xml.nonce_str"));
        param.setOpenid(p.getValue("xml.openid"));
        param.setProduct_id(p.getValue("xml.product_id"));
        param.setSign(p.getValue("xml.sign"));

        return param;
    }

    public static WeixinQrTradeCallbackModel parseCallbackModel(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinQrTradeCallbackModel param = new WeixinQrTradeCallbackModel();

        setCommonProperties(param, p);

        param.setOpenid(p.getValue("xml.openid"));
        param.setIs_subscribe(p.getValue("xml.is_subscribe"));
        param.setTrade_type(p.getValue("xml.trade_type"));
        param.setBank_type(p.getValue("xml.bank_type"));
        param.setTotal_fee(p.getValue("xml.total_fee"));
        param.setCoupon_fee(p.getValue("xml.coupon_fee"));
        param.setFee_type(p.getValue("xml.fee_type"));
        param.setTransaction_id(p.getValue("xml.transaction_id"));
        param.setOut_trade_no(p.getValue("xml.out_trade_no"));
        param.setAttach(p.getValue("xml.attach"));
        param.setTime_end(p.getValue("xml.time_end"));
        param.setSub_mch_id(p.getValue("xml.sub_mch_id"));
        param.setCash_fee(p.getValue("xml.cash_fee"));

        return param;
    }

    /**
     * 解析统一支付接口返回文本
     * 
     * @param xmlStr
     * @return
     */
    public static WeixinUnifiedOrderRespParam parsePreCreate(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinUnifiedOrderRespParam param = new WeixinUnifiedOrderRespParam();

        setCommonProperties(param, p);

        param.setTrade_type(p.getValue("xml.trade_type"));
        param.setPrepay_id(p.getValue("xml.prepay_id"));
        param.setCode_url(p.getValue("xml.code_url"));

        return param;
    }

    /**
     * 解析收单状态查询接口返回文本
     * 
     * @param xmlStr
     * @return
     */
    public static WeixinQueryStatusRespParam parseQueryStatus(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinQueryStatusRespParam param = new WeixinQueryStatusRespParam();

        setCommonProperties(param, p);

        param.setTrade_state(p.getValue("xml.trade_state"));
        param.setTrade_state_desc(p.getValue("xml.trade_state_desc"));
        param.setDevice_info(p.getValue("xml.device_info"));
        param.setOpenid(p.getValue("xml.openid"));
        param.setIs_subscribe(p.getValue("xml.is_subscribe"));
        param.setTrade_type(p.getValue("xml.trade_type"));
        param.setBank_type(p.getValue("xml.bank_type"));
        param.setTotal_fee(p.getValue("xml.total_fee"));
        param.setCoupon_fee(p.getValue("xml.coupon_fee"));
        param.setFee_type(p.getValue("xml.fee_type"));
        param.setTransaction_id(p.getValue("xml.transaction_id"));
        param.setOut_trade_no(p.getValue("xml.out_trade_no"));
        param.setAttach(p.getValue("xml.attach"));
        param.setTime_end(p.getValue("xml.time_end"));
        param.setCash_fee(p.getValue("xml.cash_fee"));

        return param;
    }

    /**
     * 解析收单状态查询接口返回文本
     * 
     * @param xmlStr
     * @return
     */
    public static WeixinBaseRespParam parseOrderClose(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinQueryStatusRespParam param = new WeixinQueryStatusRespParam();

        setCommonProperties(param, p);

        return param;
    }

    /**
     * 解析收单状态查询接口返回文本
     * 
     * @param xmlStr
     * @return
     */
    public static WeixinShortUrlRespParam parseShortUrl(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinShortUrlRespParam param = new WeixinShortUrlRespParam();

        setCommonProperties(param, p);

        param.setShort_url(p.getValue("xml.short_url"));

        return param;
    }

    /**
     * 解析通用返回属性
     * 
     * @param param
     * @param p
     */
    private static void setCommonProperties(WeixinBaseRespParam param, XmlDomParser p) {
        param.setAppid(p.getValue("xml.appid"));
        param.setErr_code(p.getValue("xml.err_code"));
        param.setErr_code_des(p.getValue("xml.err_code_des"));
        param.setMch_id(p.getValue("xml.mch_id"));
        param.setSub_appid(p.getValue("xml.sub_appid"));
        param.setSub_mch_id(p.getValue("xml.sub_mch_id"));
        param.setNonce_str(p.getValue("xml.nonce_str"));
        param.setResult_code(p.getValue("xml.result_code"));
        param.setReturn_code(p.getValue("xml.return_code"));
        param.setReturn_msg(p.getValue("xml.return_msg"));
        param.setSign(p.getValue("xml.sign"));
    }

    /**
     * 解析统一支付接口返回文本
     * 
     * @param xmlStr
     * @return
     */
    public static WeixinMicroReverseRespParam parseReverse(String xmlStr) {
        XmlDomParser p = new XmlDomParser(xmlStr);
        WeixinMicroReverseRespParam param = new WeixinMicroReverseRespParam();

        setCommonProperties(param, p);

        param.setRecall(p.getValue("xml.recall"));

        return param;
    }
}
