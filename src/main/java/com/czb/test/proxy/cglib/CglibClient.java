package com.czb.test.proxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

public class CglibClient {

    public static void main(String[] args) {
        UserServiceImpl target = new UserServiceImpl();

        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./code");
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(UserServiceImpl.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new CglibInterceptor(target));
        // 创建代理对象
        UserServiceImpl userService = (UserServiceImpl)enhancer.create();
        // 通过代理对象调用目标方法
        userService.say();
        userService.finalMethod();
        UserServiceImpl.staticMethod();
    }

}
