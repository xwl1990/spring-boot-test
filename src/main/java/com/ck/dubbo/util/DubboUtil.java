package com.ck.dubbo.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.MDC;

import com.alibaba.dubbo.common.beanutil.JavaBeanDescriptor;
import com.alibaba.dubbo.common.beanutil.JavaBeanSerializeUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ck.common.GenericResponseDto;
import com.ck.dubbo.cst.DubboCst;
/**
 * Description: 
 *
 * @author: xieweili
 * @since: 2017年1月11日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class DubboUtil {
    
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    private static final List<String> reqExcludes = new CopyOnWriteArrayList<String>();

    static {
        getReqExcludes().add(DubboCst.REQUEST_HEADER);
    }

    private DubboUtil() throws Exception {
        throw new Exception("Can not instance.");
    }
    
    /**
     * 获取请求参数属性过滤列表
     * @param fieldName
     */
    public static List<String> getReqExcludes() {
        return reqExcludes;
    }
    
    public static boolean isPrimitive(Class<?> cls) {
        return cls.isPrimitive() || cls == String.class || cls == Boolean.class || cls == Character.class || Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls);
    }

    /**
     * 记录访问日志
     * @param serviceId
     * @param startTime
     * @param endTime
     * @return
     */
    public static Map<String, String> getAccessMap(Result result, String serviceId, long start, long end) {
        Map<String, String> accessMap = new HashMap<String, String>();
        // 截取日志信息
        String transactionIdMdc = MDC.get(DubboCst.TRANSACTION_ID_KEY);
        if (!StringUtils.isBlank(transactionIdMdc)) {
            accessMap.put("transactionId", transactionIdMdc);
        }

        accessMap.put("serviceId", serviceId);
        accessMap.put("timeDiff", String.valueOf(end - start));

        accessMap.put("startTime", getFormatDate(new Date(start)));
        accessMap.put("endTime", getFormatDate(new Date(end)));

        pushResult(result, accessMap);
        return accessMap;
    }
    
    private static String getFormatDate(/*Instant in*/Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_1);
        return sdf.format(date);
    }
    
    /**
     * 处理访问日志结果
     * @param respDto
     * @param resultMap
     */
    public static void pushResult(Result result, Map<String, String> resultMap) {
        if (result == null || result.getValue() == null || isPrimitive(result.getValue().getClass())) {
            return;
        }

        GenericResponseDto respDto = getSerializeObject(result.getValue());
        resultMap.put(DubboCst.RESULT_CODE, respDto.getResultCode());
        resultMap.put(DubboCst.ERROR_CODE, respDto.getErrorCode());
    }
    
    /**
     * 序列化返回
     * @param obj
     * @return
     */
    public static GenericResponseDto getSerializeObject(Object obj) {
        if (obj instanceof JavaBeanDescriptor) {
            return (GenericResponseDto) JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) obj);
        } else if (obj instanceof GenericResponseDto) {
            return (GenericResponseDto) obj;
        } else {
            GenericResponseDto resp = new GenericResponseDto();
            Map<String, Object> map = jsonToMap(obj);
            if (map != null && !map.isEmpty()) {
                resp.putAll(map);
                return resp;
            }

            return resp;

        }
    }
    
    /**
     * 转成map
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> jsonToMap(Object obj) {
        if (obj == null) {
            return null;
        }

        String jsonString = JSON.toJSONString(obj, DubboUtil.getReqExcludeFilters(), SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.SortField);
        return JSON.parseObject(jsonString, Map.class);
    }
    
    /**
     * 获取忽略json属性过滤器列表
     * @return
     */
    public static SerializeFilter[] getReqExcludeFilters() {
        List<SerializeFilter> filters = new ArrayList<SerializeFilter>();

        for (String item : reqExcludes) {
            SimplePropertyPreFilter preFilter = new SimplePropertyPreFilter();
            preFilter.getExcludes().add(item);
            filters.add(preFilter);
        }
        SerializeFilter[] filterArray = new SerializeFilter[filters.size()];
        return filters.toArray(filterArray);
    }
}

	