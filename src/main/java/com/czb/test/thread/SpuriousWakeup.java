package com.czb.test.thread;

public class SpuriousWakeup {

    public static void main(String[] args) {
        WareHouse wareHouse = new WareHouse();
        Producer producer = new Producer(wareHouse);
        Customer customer = new Customer(wareHouse);

        new Thread(producer, "ProducerA").start();
        new Thread(producer, "ProducerB").start();

        new Thread(customer, "ConsumerC").start();
        new Thread(customer, "ConsumerD").start();
    }
}
