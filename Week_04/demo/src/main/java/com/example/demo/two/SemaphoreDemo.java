package com.example.demo.two;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 21:28
 */
public class SemaphoreDemo {
    static Semaphore s = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(SemaphoreDemo::test).start();
        }

    }

    public static void test() {
        try {
            s.acquire();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            s.release();
        }
    }

}
