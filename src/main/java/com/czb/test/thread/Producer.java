package com.czb.test.thread;

/**
 * @Description:
 * @author:czb
 * @date: 2023/4/25
 * @time: 17:40
 */
public class Producer implements Runnable{
    private WareHouse wareHouse;

    public Producer(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            wareHouse.purchase();
        }
    }
}
