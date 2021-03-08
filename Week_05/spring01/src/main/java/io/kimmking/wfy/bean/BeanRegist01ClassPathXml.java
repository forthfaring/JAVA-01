package io.kimmking.wfy.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:04
 */
public class BeanRegist01ClassPathXml {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("wfy/applicationContext.xml");
        UserBean u = context.getBean(UserBean.class);
        u.intro();
    }
}
