package com.ck.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="falchion123@gmail.com">liuzhonghui</a>
 */
public class SignatureUtils {

    private final static Logger LOG = LoggerFactory.getLogger(SignatureUtils.class);

    /**
     * MD5
     */
    public static final String SIGNATURE_MD5 = "MD5";

    /**
     * RSA
     */
    public static final String SIGNATURE_RSA = "RSA";

    /**
     * DSA
     */
    public static final String SIGNATURE_DSA = "DSA";

    /**
     * MD5
     */
    public static final String MD5 = "1";

    /**
     * RSA
     */
    public static final String RSA = "2";

    /**
     * DSA
     */
    public static final String DSA = "3";

    /**
     * 签名.
     * 
     * @param source
     * @param signType
     * @param inputCharset
     * @param key
     * @return
     */
    public static String sign(String source, String signType, String key) {
        String data = "";
        //LOG.info("source is {},key is {}", source, key);
        try {

            String charset = "UTF-8";
            if (signType.equalsIgnoreCase(SIGNATURE_MD5)) {
                String src = source + key;
                System.out.println("\nsginData:\n"+src+"\n");
                data = CryptUtils.encryptToMD5(src.getBytes(charset));

            } else if (signType.equalsIgnoreCase(SIGNATURE_RSA)) {

            }
            return data;
        } catch (Exception e) {
            LOG.error("Error when signature, errmsg: ", e);
        }
        return data;
    }

    public static void main(String[] args) {
        String a =
                "appid=wx92f6dbb646e17dc5&bank_type=CFT&cash_fee=1&fee_type=CNY&is_subscribe=Y&mch_id=10012102&nonce_str=5GDXmx2jVhVMOWgr&openid=owqpSuHS_ok6llDWfSF8ClKYzxb4&out_trade_no=99186385036098481459&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&sub_mch_id=1241309202&time_end=20160219162200&total_fee=1&trade_state=SUCCESS&trade_type=MICROPAY&transaction_id=1000700454201602193383647094";
        System.out.println(sign(a, "MD5", "&key=5b3e1d34893ce4be665bdd1821743d5d"));
    }

    /**
     * @param source
     * @param signMsg
     * @param signType
     * @param key
     * @param inputCharset
     * @return
     */
    public static boolean verify(String source, String signMsg, String signType, String key) {
        boolean ret = false;

        String data = null;
        String charset ="UTF-8";
        try {
            if (signType.equalsIgnoreCase(SIGNATURE_MD5)) {
                String src = source + key;
                data = CryptUtils.encryptToMD5(src.getBytes(charset));

                if (signMsg.equalsIgnoreCase(data)) {
                    ret = true;
                }
            } else if (signType.equalsIgnoreCase(SIGNATURE_RSA)) {
                
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error when verify msg, errmsg: " + e.getMessage(), e);
        }

        return ret;
    }

    /**
     * @param params
     * @return
     */
    public static String getContent(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        boolean first = true;
        Object value = null;
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            value = params.get(key);
            if (null == value || "".equals(value) || "null".equals(value)) {
                continue;
            }
            if (first) {
                sb.append(key).append("=").append(value);
                first = false;
            } else {
                sb.append("&").append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }
}
