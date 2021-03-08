package io.kimmking.wfy.jdkaop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 10:30
 */
@Data
@AllArgsConstructor
public class JdkDynamicProxy<T> implements InvocationHandler {

    private Object target;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin log");
        Object ret = method.invoke(target, args);
        System.out.println("end log");
        return ret;
    }

    public IBussiness getProxy(Class<IBussiness> clazz) {
        return (IBussiness) Proxy.newProxyInstance
                (clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    public static void main(String[] args) {
        IBussiness b = new Bussiness();
        JdkDynamicProxy<IBussiness> jdkDynamicProxy = new JdkDynamicProxy<>(b);
        b = jdkDynamicProxy.getProxy(IBussiness.class);
        b.work();
    }
}
