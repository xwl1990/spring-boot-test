package com.ck.model;

/**
 * Description: 基本请求参数
 *
 * @author: xieweili
 * @since: 2016年12月31日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinBaseReqParam extends WeixinBaseParam {

    /**
     * 应用ID
     */
    private String appCode;
    
    /**
     * 商户号
     */
    private String mchtNo;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    
    public String getMchtNo() {
        return mchtNo;
    }
    
    public void setMchtNo(String mchtNo) {
        this.mchtNo = mchtNo;
    }
    
}
