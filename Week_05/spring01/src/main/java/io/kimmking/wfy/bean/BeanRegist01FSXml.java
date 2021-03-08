package io.kimmking.wfy.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:04
 */
public class BeanRegist01FSXml {
    public static void main(String[] args) {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("D:\\workspace\\JAVA-01\\Week_05\\spring01\\src\\main\\resources\\wfy\\applicationContext.xml");
        UserBean u = context.getBean(UserBean.class);
        u.intro();
    }
}
