package com.example.demo.one;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-06 23:35
 */
public class Method08CompletableFuture {
    public static void main(String[] args) {
        CompletableFuture<Integer> c = CompletableFuture.supplyAsync(PublicMethod::sum);
        try {
            System.out.println(c.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
