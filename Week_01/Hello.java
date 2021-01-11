package org.wfy.grow.geektime.java.jvm;

/**
 * @program: grow
 * @description:
 * @author: wfy
 * @create: 2021-01-11 22:38
 */
public class Hello {

    public static void main(String[] args) {
        Hello h = new Hello();
        int x = 10;
        int y = 5;
        System.out.println(h.add(x, y));
        System.out.println(sub(x, y));
        System.out.println(mul(x, y));
        int d = div(x, y);
        System.out.println(div(x, y));
        if (d == 2) {
            System.out.println(true);
        }
        for (int i = 0; i < x; i++) {
            System.out.println(i);
        }
    }

    private int add(int x, int y) {
        return x + y;
    }

    private static int sub(int x, int y) {
        return x - y;
    }

    private static int mul(int x, int y) {
        return x * y;
    }

    private static int div(int x, int y) {
        return x / y;
    }
}
