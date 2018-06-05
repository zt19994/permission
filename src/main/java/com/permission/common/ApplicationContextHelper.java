package com.permission.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zt1994 2018/6/5 19:54
 */
@Component("applicationContext")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> tClass){
        if (applicationContext == null){
            return null;
        }
        return applicationContext.getBean(tClass);
    }

    public static <T> T popBean(String name, Class<T> tClass){
        if (applicationContext ==null){
            return null;
        }
        return applicationContext.getBean(name, tClass);
    }
}
