package com.example.demo.one;

import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description: 管程+wait notify
 * @author: wfy
 * @create: 2021-02-06 22:12
 */
public class Method02SynchronizedWait {
    static Integer value;


    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                value = PublicMethod.sum();
                lock.notifyAll();
            }
        });
        t1.start();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(value);
    }
}
