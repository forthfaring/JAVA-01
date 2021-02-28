package com.example.demo.two;

import java.util.concurrent.locks.LockSupport;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-28 16:53
 */
public class LockSupportDemo {
    public static void main(String[] args) {
//        LockSupport.park(null);
//        LockSupport.parkUntil(System.currentTimeMillis() + 5000);
        LockSupport.parkNanos(10000000);
//        LockSupport.unpark(Thread);
        System.out.println("123");
    }
}
