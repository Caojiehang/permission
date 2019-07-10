package com.jiehang.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationContextHelper
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-10 23:07
 **/
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> tClass) {
        if(applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(tClass);
    }

    public static <T> T popBean(String name, Class<T> tClass) {
        if(applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name,tClass);
    }
}
