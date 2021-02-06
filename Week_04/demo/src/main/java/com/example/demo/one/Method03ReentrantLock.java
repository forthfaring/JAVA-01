package com.example.demo.one;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JAVA-01
 * @description join
 * @author: wfy
 * @create: 2021-02-06 21:59
 */
public class Method03ReentrantLock extends Thread {
    static Integer value;
    static Lock lock = new ReentrantLock(true);
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                value = PublicMethod.sum();
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(value);
    }
}


