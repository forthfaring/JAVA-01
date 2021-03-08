package io.kimmking.wfy.jdkaop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 13:46
 */
public class CglibProxy implements MethodInterceptor {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();//创建一个代理工具类
        enhancer.setSuperclass(Bussiness.class);//设置一个模拟的父类
        enhancer.setCallback(new CglibProxy());
        IBussiness b = (IBussiness) enhancer.create();
        System.out.println(b.work());
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("Before Method Invoke");
        Object ret = proxy.invokeSuper(o, objects);
        System.out.println("After Method Invoke");
        return ret;
    }
}
