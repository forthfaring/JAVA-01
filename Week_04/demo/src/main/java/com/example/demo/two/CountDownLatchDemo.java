package com.example.demo.two;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 21:32
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(2);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            c.countDown();
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            c.countDown();
        }).start();

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main");
    }
}
