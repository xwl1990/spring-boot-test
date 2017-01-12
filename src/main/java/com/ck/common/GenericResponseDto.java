package com.ck.common;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ck.dubbo.cst.DubboCst;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月11日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class GenericResponseDto extends HashMap<String, Object> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 870714850748751859L;

    public static final String RESPONSE_OK = "1";

    public void setResultCode(String resultCode) {
        put(DubboCst.RESULT_CODE, resultCode);
    }

    public String getResultCode() {
        return getStringData(DubboCst.RESULT_CODE);
    }

    public void setErrorCode(String errorCode) {
        put(DubboCst.ERROR_CODE, errorCode);
    }

    public String getErrorCode() {
        return getStringData(DubboCst.ERROR_CODE);
    }

    public void setErrorDesc(String errorDesc) {
        put(DubboCst.ERROR_DESC, errorDesc);
    }

    public String getErrorDesc() {
        return getStringData(DubboCst.ERROR_DESC);
    }

    public Object getData(String key) {
        return get(key);
    }

    public String getStringData(String key) {
        Object obj = getData(key);
        if (null != obj) {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj instanceof Integer) {
                Integer data = (Integer) obj;
                return String.valueOf(data);
            }

            else if (obj instanceof Long) {
                Long data = (Long) obj;
                return String.valueOf(data);
            } else {
                return (String) obj;
            }
        }
        return null;
    }

    public Long getLongData(String key) {
        Object obj = getData(key);
        if (null != obj) {
            if (obj instanceof String) {
                String data = (String) obj;
                return Long.valueOf(data);
            } else if (obj instanceof Integer) {
                Integer data = (Integer) obj;
                return Long.valueOf(data);
            }

            else if (obj instanceof Long) {
                return (Long) obj;
            } else {
                return (Long) obj;
            }
        }

        return null;
    }

    public Integer getIntegerData(String key) {
        Object obj = getData(key);
        if (null != obj) {
            if (obj instanceof String) {
                String data = (String) obj;
                return Integer.valueOf(data);
            } else if (obj instanceof Integer) {
                return (Integer) obj;
            } else {
                return (Integer) obj;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T parseData(String key, Class<T> clazz) {

        Object obj = get(key);
        if (obj instanceof JSONObject) {
            return JSON.toJavaObject((JSONObject) obj, clazz);
        }

        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        return (List<String>) getData(key);
    }

    public Object putData(String key, Object value) {
        return put(key, value);
    }

    public boolean isOk() {
        if (!this.isEmpty() && RESPONSE_OK.equals(getResultCode())) {
            return true;
        }

        return false;
    }

    /**
     * 设置成功标识
     */
    public void setSuccess() {
        setResultCode(RESPONSE_OK);
    }

    @Override
    public String toString() {
        try {
            return JSON.toJSONString(this);
        } catch (Exception e) {

        }

        return null;
    }

}
