package com.example.demo.first;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/26 15:20
 */
public class ExecutorDemo01 {
    public static void main(String[] args) {
        //
        ExecutorService executor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
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

    /**
     * @Description: 测试submit execute future的用法
     * @Param: []
     * @return: void
     * @Author: wanfy
     * @Date: 2021/2/28 00:02
     */
    @Test
    public void test01() {
        ExecutorService executor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        executor.execute(() -> {
            System.out.println(123);
        });
        Future<String> f2 = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "succ");
        try {
            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Future<String> f3 = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "callable";
        });
        System.out.println(f3.isDone());
        try {
            System.out.println(f3.get());
//            System.out.println(f3.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        catch (TimeoutException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * @Description: 测试submit和execute的异常处理机制
     * @Param: []
     * @return: void
     * @Author: wfy
     * @Date: 2021/2/28 00:01
     */
    @Test
    public void test02() {
        ExecutorService executor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
//        executor.execute(() -> {
//            System.out.println(Integer.parseInt("1"));
//            System.out.println(Integer.parseInt("dsd"));
//        });
        Future<String> f2 = executor.submit(() -> {
            System.out.println(Integer.parseInt("1"));
            System.out.println(Integer.parseInt("submit dsd"));
        }, "succ");
        try {
            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    @Test
    public void test03() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(true));
        for (int i = 0; i < 11; i++) {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        while (true) {
            System.out.println("executing:" + executor.getActiveCount());
            System.out.println("queue size:" + executor.getQueue().size());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
