package com.example.demo.two;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 21:35
 */
public class CylicBarrierDemo {
    static CyclicBarrier b = new CyclicBarrier(2, () -> {
        System.out.println("main complete");
    });

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(111);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                b.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("111" + Thread.currentThread().getName());
        }).start();
        new Thread(() -> {
            System.out.println(222);
            try {
                b.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("222" + Thread.currentThread().getName());
        }).start();


        System.out.println("main");
        test();
    }


    public static void test() {
        new Thread(() -> {
            System.out.println(111);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                b.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("111" + Thread.currentThread().getName());
        }).start();
        new Thread(() -> {
            System.out.println(222);
            try {
                b.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("222" + Thread.currentThread().getName());
        }).start();


        System.out.println("main");
    }


}
