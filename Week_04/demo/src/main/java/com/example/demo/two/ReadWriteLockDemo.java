package com.example.demo.two;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 16:46
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock r = lock.readLock();
        Lock w = lock.writeLock();
        
    }
}
