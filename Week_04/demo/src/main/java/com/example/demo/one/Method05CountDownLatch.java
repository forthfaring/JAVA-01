package com.example.demo.one;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-06 23:12
 */
public class Method05CountDownLatch {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CountDownLatch c = new CountDownLatch(1);
        int[] holder = new int[1];
        executor.submit(() -> {
            holder[0] = PublicMethod.sum();
            c.countDown();
        });
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(holder[0]);
        executor.shutdown();
    }
}
