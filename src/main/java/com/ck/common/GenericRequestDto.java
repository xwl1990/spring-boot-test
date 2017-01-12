package com.ck.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月11日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class GenericRequestDto extends HashMap<String, Object> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6810272545220742760L;

    /**
     * 每次请求只有一个
     */
    private String transactionId;

    private transient Map<String, Object> innerMap = new HashMap<String, Object>();

    /**
     * 请求参数附加会话信息
     */
    private GenericSessionContext session;

    public String getServiceId() {
        return getStringData("serviceId");
    }

    public void setServiceId(String serviceId) {
        put("serviceId", serviceId);
    }

    public Map<String, Object> getInnerMap() {
        return innerMap;
    }

    public void setInnerMap(Map<String, Object> innerMap) {
        this.innerMap = innerMap;
    }

    public Object getInnerParameter(String key) {
        return innerMap.get(key);
    }

    public String getStringInnerParameter(String key) {
        return (String) getInnerParameter(key);
    }

    public void putInnerParameter(String key, Object value) {
        innerMap.put(key, value);
    }

    public GenericRequestHeader getRequestHeader() {
        return (GenericRequestHeader) get("requestHeader");
    }

    public void setRequestHeader(GenericRequestHeader requestHeader) {
        put("requestHeader", requestHeader);
    }

    public void removeData(String key) {
        remove(key);
    }

    public Object getData(String key) {
        return get(key);
    }

    public void putData(String key, Object value) {
        put(key, value);
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

    public GenericSessionContext getSession() {
        if (session == null) {
            session = new GenericSessionContext();
        }

        return session;
    }

    public void setSession(GenericSessionContext session) {
        this.session = session;
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

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;

    }

    public String getTransactionId() {
        return this.transactionId;
    }

    @SuppressWarnings("unchecked")
    public <T> T parseData(String key, Class<T> clazz) {

        Object obj = get(key);
        if (obj instanceof JSONObject) {
            return JSON.toJavaObject((JSONObject) obj, clazz);
        }

        return (T) obj;
    }

    @Override
    public GenericRequestDto clone() {
        GenericRequestDto requestObject = null;
        requestObject = (GenericRequestDto) super.clone();
        return requestObject;
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
