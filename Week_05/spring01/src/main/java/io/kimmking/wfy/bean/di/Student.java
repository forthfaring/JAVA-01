package io.kimmking.wfy.bean.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 13:25
 */
@Component
public class Student {
    private Pen pen;

    @Autowired
    public Student(Pen pen) {
        this.pen = pen;
    }

    public void write() {
        pen.write();
    }

    public void addWater() {
        System.out.println("加墨水");
    }
}
