package com.example.demo.first;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 00:33
 */
public class ExecutorDemo02 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10,
                0L, TimeUnit.MILLISECONDS,
//                new SynchronousQueue<>(true));
                new LinkedBlockingQueue<>(1));
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
//            System.out.println("queue size:" + executor.getQueue().size());
        }

        while (true) {
            System.out.println("executing:" + executor.getActiveCount());
            System.out.println("queue size:" + executor.getQueue().size());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
