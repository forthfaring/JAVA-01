package io.kimmking.wfy.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:06
 */
@Data
public class UserBean {
    private String name;
    private int age;

    public UserBean() {
    }

    public void intro() {
        System.out.println("intro>>>" + name + age);
    }
}
