package org.wfy.grow.geektime.java.jvm;

import java.io.FileInputStream;

/**
 * @program: 2、自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内 容是一个 Hello.class 文件所有字节(x=255-x)处理后的文件。文件群里提供。
 * @description:
 * @author: wfy
 * @create: 2021-01-11 21:57
 */
public class XClassLoader {
    public static void main(String[] args) {
        ClassLoader helloClassLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    FileInputStream f = new FileInputStream("/Users/wfy/work/study/grow/src/main/java/org/wfy/grow/geektime/java/jvm/Hello.xlass");
                    byte[] arr = new byte[f.available()];
                    f.read(arr);
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = (byte) (0xff - arr[i]);
                    }
                    return defineClass(name, arr, 0, arr.length);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        try {
            Object o = helloClassLoader.loadClass("Hello").newInstance();
            o.getClass().getMethod("hello").invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
