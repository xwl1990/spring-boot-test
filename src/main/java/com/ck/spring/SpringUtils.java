package com.ck.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationCtontext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringUtils.class);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("inject applicationContext:{}", applicationContext.getApplicationName());
        System.out.println("setApplicationContext");
        if (applicationContext != null) {
            System.out.println("setApplicationContext11");
            logger.warn("ApplicationContext is overide, old ApplicationContext:" + applicationContext.getApplicationName());
        }

        SpringUtils.applicationCtontext = applicationContext;
    }

    /**
     * getApplicationContext:获取当前运行的Spring容器
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationCtontext;
    }

    /**
     * 
     * getBean:根据类型从容器中获取bean对象
     * 
     * @param name
     * @param requiredType
     * @return
     * @since JDK 1.6
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     * 
     * getBean:根据类型从容器中获取bean对象
     * 
     * @param name
     * @param requiredType
     * @return
     * @since JDK 1.6
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 
     * getBean:根据类型从容器中获取bean对象
     * 
     * @param name
     * @param requiredType
     * @return
     * @since JDK 1.6
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    public static ApplicationContext getApplicationCtontext() {
        return applicationCtontext;
    }

    public static void setApplicationCtontext(ApplicationContext applicationCtontext) {
        SpringUtils.applicationCtontext = applicationCtontext;
    }
}
