package com.example.demo.two;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 15:45
 */
public class LockDemo01 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
//        executor.execute(() -> {
//            lock.lock();
//            try {
//                c1.await();
//                System.out.println(Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        });

        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
//                c1.await();
//                c1.signal();
                reEnter(lock);
                System.out.println("===" + Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        });
        System.out.println("main");
        executor.shutdown();

    }

    public static void reEnter(Lock lock) {
        lock.lock();
        try {
//                c1.await();
//                c1.signal();
            System.out.println("===" + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }
}
