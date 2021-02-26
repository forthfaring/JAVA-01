package com.example.demo.first;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/26 15:10
 */
public class ThreadDemo01 {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
//            System.out.println("id:" + Thread.currentThread().getId());
//            System.out.println("name:" + Thread.currentThread().getName());
            System.out.println("go");
        }, "worker-01");
        t.start();
//        System.out.println("activeCount:" + Thread.activeCount());
        System.out.println(Thread.currentThread().getState());
    }
}
