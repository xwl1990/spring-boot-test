package com.ck.util;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Description:
 *
 * @author: xieweili
 * @since: 2017年1月12日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */

public class RequestUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RequestUtils.class);

    private static final IdWorker idWorker = new IdWorker(ThreadLocalRandom.current().nextInt(0, 15));

    /**
     * 
     * @param request
     * @param key
     * @param val
     */
    public static void setSessionAttribute(HttpServletRequest request, String key, Object val) {
        HttpSession session = request.getSession();
        session.setAttribute(key, val);
    }

    /**
     * 
     * @param request
     * @param key
     * @return
     */
    public static Object getSessionAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * 读取请求中的xml数据
     * 
     * @param request
     * @return
     */
    public static String readXMLFromRequestBody(HttpServletRequest request) {
        StringBuffer xml = new StringBuffer();
        String line = null;

        try (BufferedReader reader = request.getReader()) {
            if (reader == null) {
                return null;
            }

            while ((line = reader.readLine()) != null) {
                xml.append(line.trim());
            }

            return xml.toString();
        } catch (Exception e) {
            LOG.error("readXMLFromRequestBody throw error:", e);
        }

        return null;
    }

    /**
     * 
     * @param request
     * @param key
     */
    public static void removeSessinAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }

    /**
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest req) {
        String path = req.getContextPath();
        String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";
        return basePath;
    }

    /**
     * 获取当前请求
     * 
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr != null) {
            return attr.getRequest();
        }

        return null;
    }

    /**
     * 解析http请求参数
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> parseRequestParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            if (values == null || values.length <= 0) {
                continue;
            }
            String value = values[0];
            for (int i = 1; i < values.length; i++) {
                value += "," + values[i];
            }
            params.put(name, value);
        }
        return params;
    }

    /**
     * 是否是静态文件及脚本，排除druid监控.
     * 
     * @param ignores
     * @return
     */
    public static boolean isStaticResources(String url) {
        if (StringUtils.isBlank(url) || url.equals("/")) {
            LOG.debug("current request is null.");
            return true;
        }

        url = url.toLowerCase();
        String[] ignoreArr = { ".htm", "/swagger", ".jpeg", ".jpg", ".png", ".js", ".css", ".gif", "/druid" };
        for (String ignore : ignoreArr) {
            if (ignore.indexOf(".") >= 0 && url.indexOf(".") >= 0) {
                if (url.substring(url.indexOf("."), url.length()).equals(ignore)) {
                    return true;
                }
            } else {
                if (url.indexOf(ignore) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取http请求路径，替换"/"为"_"
     * 
     * @param request
     * @return
     */
    public static String getRequestPathKey(HttpServletRequest request) {
        return getRequestPathKey(request.getServletPath());
    }

    /**
     * 获取http请求路径，替换"/"为"_"
     * 
     * @param request
     * @return
     */
    public static String getRequestPathKey(String url) {
        if (isStaticResources(url)) {
            LOG.info("current request is absolute path or empty.");
            return null;
        }

        String path = url.replaceFirst("/", "");
        path = path.replace("/", "_");
        if (StringUtils.isNotEmpty(path)) {
            if (path.lastIndexOf(".") >= 0) {
                path = path.substring(0, path.lastIndexOf("."));
            }
        }

        return path;
    }

    /**
     * 获取请求头中的手机imei,如果没有值，取当前自增数值
     * 
     * @param request
     * @return
     */
    public static String getMobileDeviceId(HttpServletRequest request) {
        String imei = request.getHeader("imei");
        Long id = idWorker.nextId();
        return StringUtils.isNotEmpty(imei) ? imei + id : String.valueOf(id);
    }

    /**
     * 解析http请求参数
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> parseRequestAttributeParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> parameterNames = request.getAttributeNames();

        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String[] values = request.getParameterValues(name);
                if (values == null || values.length <= 0) {
                    continue;
                }
                String value = values[0];
                for (int i = 1; i < values.length; i++) {
                    value += "," + values[i];
                }
                params.put(name, value);
            }
        }
        return params;
    }

    /**
     * 解析http请求参数
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> parseSessionParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (request.getSession() != null) {
            Enumeration<String> parameterNames = request.getSession().getAttributeNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                Object value = getSessionAttribute(request, name);
                if (value == null) {
                    continue;
                }

                params.put(name, value);
            }
        }
        return params;
    }

}
