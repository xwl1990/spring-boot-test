
package com.ck.util;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * The class FreemarkerUtils.
 *
 * Description: 
 *
 * @author: xieweili
 * @since: 2016年12月28日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class FreemarkerUtils {

    private static Configuration cfg = new Configuration();

    static {
        cfg.setDefaultEncoding("UTF-8");
        cfg.setOutputEncoding("UTF-8");
        cfg.setLocale(new Locale("zh_CN"));
        cfg.setClassForTemplateLoading(FreemarkerUtils.class, "/template");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

    /**
     * 根据名字得到模版.
     * 
     * @param fileName
     * @param dataModel
     * @return
     * @throws CashboxException
     */
    public static String getContentByFileName(String fileName, Map<String, Object> dataModel) throws Exception {
        StringWriter out = new StringWriter();

        try {
            Template template = cfg.getTemplate(fileName);
            template.process(dataModel, out);
            return out.getBuffer().toString();
        } catch (Exception e) {
            throw new Exception("Template parser fail", e);
        }
    }

    /**
     * 清除模版缓存.
     */
    public static void clearTemplateCache() {
        cfg.clearTemplateCache();
    }
}

	