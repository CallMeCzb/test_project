package com.czb.test.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @author:czb
 * @date: 2023/3/25
 * @time: 14:03
 */
public class HelloInvocationHandler implements InvocationHandler {
    private Object target;
    public HelloInvocationHandler(Object target) {
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("before hello ... ");
        Object result = method.invoke(this.target, args);
        System.out.println("after hello... ");
        return result;
    }
}
