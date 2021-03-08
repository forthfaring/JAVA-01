package io.kimmking.wfy.jdkaop;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 10:31
 */
public class Bussiness implements IBussiness {
    @Override
    public String work() {
        System.out.println("good job");
        return "good job";
    }
}
