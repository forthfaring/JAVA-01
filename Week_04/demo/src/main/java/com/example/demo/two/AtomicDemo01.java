package com.example.demo.two;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 17:00
 */
public class AtomicDemo01 {
    static AtomicInteger count = new AtomicInteger(2);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            incr();
        });
        Thread t2 = new Thread(() -> {
            incr();
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    public static void incr() {
        for (int i = 0; i < 100000; i++) {
            count.incrementAndGet();
        }
    }

}
