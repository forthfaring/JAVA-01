package io.kimmking.wfy.bean.di;

import io.kimmking.wfy.bean.BeanConfiguration;
import io.kimmking.wfy.bean.UserBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:37
 */
public class StudentBeanRegistAnnotation {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
        Student s = context.getBean(Student.class);
        s.write();
    }
}
