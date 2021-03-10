package com.example.jdbcinsertimprove;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@Data
public class JdbcInsertImproveApplication {

    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(JdbcInsertImproveApplication.class, args);
        jdbcTemplate = ac.getBean(JdbcTemplate.class);
        insertBatch();
    }

    private static void insertBatch() {
//        jdbcTemplate.execute();
        //insert 100W
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from pica.p_doctor limit 1");
        System.out.println(l);
    }
}
