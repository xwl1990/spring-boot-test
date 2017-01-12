package com.ck.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月11日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class GenericSessionContext implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3476626729518233777L;

    /**
     * for update session remark
     */
    private static String SESSION_ATTRIBUTE_UPDATE = "U";

    /**
     * for remove session remark
     */
    private static String SESSION_ATTRIBUTE_REMOVE = "R";

    private Map<String, Object> ctxMap;

    private Map<String, String> transitionMap;

    public Map<String, Object> getCtxMap() {
        return ctxMap;
    }

    public void setCtxMap(Map<String, Object> ctxMap) {
        this.ctxMap = ctxMap;
    }

    public Map<String, String> getTransitionMap() {
        return transitionMap;
    }

    public void setTransitionMap(Map<String, String> transitionMap) {
        this.transitionMap = transitionMap;
    }

    public Object getData(String key) {
        if (ctxMap == null) {
            ctxMap = new HashMap<String, Object>();
        }

        return ctxMap.get(key);
    }

    public void putData(String key, Object data) {
        if (ctxMap == null) {
            ctxMap = new HashMap<String, Object>();
        }
        ctxMap.put(key, data);

        addTransition(key, SESSION_ATTRIBUTE_UPDATE);
    }

    public Object removeData(String key) {
        addTransition(key, SESSION_ATTRIBUTE_REMOVE);
        if (ctxMap != null && !ctxMap.isEmpty()) {
            return ctxMap.remove(key);
        }

        return null;
    }

    public void putAll(Map<String, Object> ctxMap) {
        if (this.ctxMap == null) {
            this.ctxMap = new HashMap<String, Object>();
        }

        this.ctxMap.putAll(ctxMap);
    }

    public String getTransitionStatus(String key) {
        return (transitionMap == null) ? null : transitionMap.get(key);
    }

    private void addTransition(String key, String value) {
        if (transitionMap == null) {
            transitionMap = new HashMap<String, String>();
        }

        transitionMap.put(key, value);
    }

    // 获取session中的对象
    @SuppressWarnings("unchecked")
    public <T> T parseData(String key, Class<T> clazz) {

        Object obj = ctxMap.get(key);
        if (obj instanceof JSONObject) {
            return JSON.toJavaObject((JSONObject) obj, clazz);
        }

        return (T) obj;

    }

    @Override
    public String toString() {
        return "GenericSessionContext [ctxMap=" + ctxMap + ", transitionMap=" + transitionMap + "]";
    }

}
