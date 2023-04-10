package com.czb.test.proxy.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @author:czb
 * @date: 2023/3/25
 * @time: 15:18
 */
public class ProxyTest {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //1. 创建被代理的目标对象
        HelloService helloService= new HelloService();
        //2. 创建调用处理器
        HelloInvocationHandler handler = new HelloInvocationHandler(new HelloService());
        //3. 获取对应的 ClassLoader
        ClassLoader classLoader = helloService.getClass().getClassLoader();
        //4. 获取所有接口的interface
        Class[] interfaces = helloService.getClass().getInterfaces();
        //5. 创建代理类
        Hello hello = (Hello) Proxy.newProxyInstance(classLoader, interfaces, handler);
        //5. 调用方法
        hello.sayHello();




//        //1. 创建被代理的目标对象
//        HelloService helloService1= new HelloService();
//        //2. 创建调用处理器
//        HelloInvocationHandler handler1 = new HelloInvocationHandler(new HelloService());
//        //3. 获取对应的 ClassLoader
//        ClassLoader classLoader1 = helloService1.getClass().getClassLoader();
//        //4. 获取所有接口的interface
//        Class[] interfaces1 = helloService1.getClass().getInterfaces();
//        //5. 创建代理类
//        Hello hello1 = (Hello) Proxy.newProxyInstance(classLoader1, interfaces1, handler1);
//        //5. 调用方法
//        hello1.sayHello();
    }
}
