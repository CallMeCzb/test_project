package com.czb.test.bean;


public class Test {
    public static void main(String[] args) {
        Integer int11 = new Integer(1);
        int int12 = 1;
        Integer int21 = new Integer(1127);
        Integer int22 = new Integer(1127);
        Integer int31 = new Integer(1127);
        int int32 = 1127;
        Integer int41 = new Integer(1288);
        Integer int42 = new Integer(1288);

        Integer int51 = new Integer(1);
        Long int52 = new Long(1);

        System.out.println(int11 == int12);
        System.out.println(int21 == int22);
        System.out.println(int21.equals(int22));
        System.out.println(int31 == int32);
        System.out.println(int41 == int42);
        System.out.println(int51.equals("1"));
    }
}
