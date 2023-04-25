package com.czb.test.thread;

/**
 * @Description:
 * @author:czb
 * @date: 2023/4/25
 * @time: 17:40
 */
public class Customer implements Runnable{
    private WareHouse wareHouse;

    public Customer(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; ++i) {
            wareHouse.outbound();
        }
    }
}
