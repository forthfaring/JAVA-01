package io.kimmking.wfy.jdkaop;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 10:31
 */
public class BussinessProxy {
    @BindingPriority(1)
    public static String workProxy1() {
        return "proxy good job!";
    }

    @BindingPriority(2)
    public static String workProxy2() {
        return "proxy good job 2!";
    }
}
