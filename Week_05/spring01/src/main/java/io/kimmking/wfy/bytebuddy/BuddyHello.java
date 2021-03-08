package io.kimmking.wfy.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 14:08
 */
public class BuddyHello {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();
        Class<?> dynamicType = unloadedType.load(BuddyHello.class
                .getClassLoader())
                .getLoaded();
        System.out.println(dynamicType.newInstance().toString());
    }
}
