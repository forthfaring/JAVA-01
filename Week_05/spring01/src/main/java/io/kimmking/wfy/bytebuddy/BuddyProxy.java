package io.kimmking.wfy.bytebuddy;

import io.kimmking.wfy.jdkaop.Bussiness;
import io.kimmking.wfy.jdkaop.BussinessProxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 14:13
 */
public class BuddyProxy {
    public static void main(String[] args) {
        try {
            String r = new ByteBuddy()
                    .subclass(Bussiness.class)
                    .method(named("work")
                            .and(isDeclaredBy(Bussiness.class)
                                    .and(returns(String.class))))
                    .intercept(MethodDelegation.to(BussinessProxy.class))
                    .make()
                    .load(BuddyProxy.class.getClassLoader())
                    .getLoaded()
                    .newInstance()
                    .work();
            System.out.println(r);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
