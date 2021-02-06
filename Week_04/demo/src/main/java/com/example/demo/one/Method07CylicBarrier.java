package com.example.demo.one;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-06 23:12
 */
public class Method07CylicBarrier {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int[] holder = new int[1];
        CyclicBarrier c = new CyclicBarrier(2, () -> {
            System.out.println(holder[0]);
        });
        executor.submit(() -> {
            holder[0] = PublicMethod.sum();
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
