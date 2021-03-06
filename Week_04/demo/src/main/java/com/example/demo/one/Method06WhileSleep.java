package com.example.demo.one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-06 23:19
 */
public class Method06WhileSleep {
    static volatile int value;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            value = PublicMethod.sum();
        });
        while (value == 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(value);
        executor.shutdown();
    }
}
