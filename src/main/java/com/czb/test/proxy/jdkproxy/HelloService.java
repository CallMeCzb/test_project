package com.czb.test.proxy.jdkproxy;

/**
 * @Description:
 * @author:czb
 * @date: 2023/3/25
 * @time: 14:03
 */
public class HelloService implements Hello {
    public void sayHello() {
        System.out.println("1=======Hello World...");
    }

    @Override
    public void sayHello(String str) {
        System.out.println("1=======Hello World...");
    }
}
