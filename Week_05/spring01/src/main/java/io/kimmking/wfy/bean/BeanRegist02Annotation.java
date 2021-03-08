package io.kimmking.wfy.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:37
 */
public class BeanRegist02Annotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        UserBean u = context.getBean(UserBean.class);
        u.intro();
    }
}
