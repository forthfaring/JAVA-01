package com.example.demo.one;

/**
 * @program: JAVA-01
 * @description join
 * @author: wfy
 * @create: 2021-02-06 21:59
 */
public class Method01Join extends Thread {
    int returnValue;

    public static void main(String[] args) {
        Method01Join t1 = new Method01Join();
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.returnValue);
    }

    @Override
    public void run() {
        returnValue = PublicMethod.sum();
    }


}
