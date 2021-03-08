package io.kimmking.wfy.bean.di;

import io.kimmking.wfy.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 13:26
 */
@Component
public class Pen {
    @Autowired
    @Qualifier("student")
//    @Resource(name = "student")
    private Student student;

    @Autowired(required = false)
    private UserBean user;

//    @Autowired
//    public Pen(Student student) {
//        this.student = student;
//    }

    public void write() {
        student.addWater();
        System.out.println("写作文");
    }
}
