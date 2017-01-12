package com.ck.model;

/**
 * 短链接响应参数 Description:
 *
 * @author: xieweili
 * @since: 2017年1月10日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinShortUrlRespParam extends WeixinBaseRespParam {

    private String short_url;

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

}
