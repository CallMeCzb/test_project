package com.czb.test.thread;

/**
 * @Description:
 * @author:czb
 * @date: 2023/4/25
 * @time: 17:40
 */
public class WareHouse{
    private volatile int product = 0;

    // 入库
    public synchronized void purchase() {
        // 库存已满，仓库最多容纳1个货品
        while (product > 0) {
            System.out.println(Thread.currentThread().getName() + ": " + "已满！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                // ignore exception
            }
        }
        ++product;
        // 该线程从while中出来的时候，已满足条件
        System.out.println(Thread.currentThread().getName() + ": " + "-------------入库成功，余货：" + product);
        this.notifyAll();
    }

    // 出库
    public synchronized void outbound() {
        while (product <= 0) {
            System.out.println(Thread.currentThread().getName() + ": " + "库存不足，无法出库");
            try {
                this.wait();
            } catch (InterruptedException e) {
                // ignore exception
            }
        }
        --product;
        System.out.println(Thread.currentThread().getName() + ":出库成功，余货：" + product);
        this.notifyAll();
    }
}
