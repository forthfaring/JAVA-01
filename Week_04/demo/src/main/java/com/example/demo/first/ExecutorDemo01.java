package com.example.demo.first;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/26 15:20
 */
public class ExecutorDemo01 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
//        try {
//            executor.execute(() -> {
//                System.out.println("execute异步执行");
//                throw new RuntimeException();
//            });
//        } catch (Exception e) {
//            System.out.println("exec error");
//        }
        try {
            executor.submit(() -> {
                System.out.println("submit异步执行");
//                throw new RuntimeException();
            });
        } catch (Exception e) {
            System.out.println("submit error");
        }

        executor.shutdown();
//        System.out.println(executor.shutdownNow());
    }
}
