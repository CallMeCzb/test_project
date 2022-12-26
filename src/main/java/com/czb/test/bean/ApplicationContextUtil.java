package com.czb.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author:czb
 * @date: 2022/12/8
 * @time: 22:13
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过bean的id获取bean对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据bean的id和类型获取bean对象
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName,Class<T> clazz){
        return clazz.cast(getBean(beanName));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
