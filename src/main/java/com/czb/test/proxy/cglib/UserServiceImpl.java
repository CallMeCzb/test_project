package com.czb.test.proxy.cglib;

public class UserServiceImpl implements UserService{

    @Override
    public void say() {
        System.out.println("hello");
        say2();
    }

    @Override
    public void say2() {
        System.out.println("hello2");
    }

    public final void finalMethod() {
        System.out.println("final method");
    }

    public static void staticMethod() {
        System.out.println("static method");
    }
}
