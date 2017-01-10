package com.ck.model;


/**
 * Description: 被扫撤销订单响应参数
 *
 * @author: xieweili
 * @since: 2016年12月31日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinMicroReverseRespParam extends WeixinBaseRespParam {

    /**
     * 是否重调, Y-需要，N-不需要
     */
    private String recall;

    /**
     * 交易状态，内部处理变量, 0:交易成功，3：撤销成功, 4:已确认失败
     */
    private String tradeStatus;

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

}
