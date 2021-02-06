package com.example.demo.one;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-02-06 22:01
 */
public class PublicMethod {
    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
