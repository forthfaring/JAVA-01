package com.example.demo.one;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/2 18:21
 */
public class Method10FutureTask {
    public static void main(String[] args) {
        FutureTask task = new FutureTask(PublicMethod::sum);
        Thread t = new Thread(task);
        t.start();
        try {
            System.out.println(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
