package com.ck.model;

/**
 * Description: 基本响应参数
 *
 * @author: xieweili
 * @since: 2016年12月31日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class WeixinBaseRespParam extends WeixinBaseParam {

    /**
     * 返回状态码SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code 来判断
     */
    private String return_code;

    /**
     * 返回信息 返回信息，如非空，为错误原因 签名失败 参数格式校验错误
     */
    private String return_msg;

    /**
     * 业务结果 SUCCESS/FAIL
     */
    private String result_code;

    /**
     * 业务错误代码
     */
    private String err_code;

    /**
     * 业务错误代码描述
     */
    private String err_code_des;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

}
