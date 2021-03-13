package com.example.sharding01;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Data
public class Sharding01Application {

    private static JdbcTemplate jdbcTemplate;
    private static DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext ac = SpringApplication.run(Sharding01Application.class, args);
        jdbcTemplate = ac.getBean(JdbcTemplate.class);
        dataSource = ac.getBean(DataSource.class);
        insertBatchJdbc();
    }

    private static void insertBatchJdbc() throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement stat = conn.prepareStatement("insert into t_order (order_no,price,order_status,pay_status,fail_reason,created_time,updated_time,created_id,updated_id,user_id) values(?,?,?,?,?,?,?,?,?,?)");
        //insert 100W
        System.out.println("start insert");
        StopWatch s = new StopWatch();
        s.start();
        List<Object[]> var2 = new ArrayList<>();
        for (int x = 0; x < 1; x++) {
            for (int i = 0; i < 64; i++) {
                stat.setString(1, i + x * 1000 + "NO");
                stat.setInt(2, 10);
                stat.setInt(3, 1);
                stat.setInt(4, 1);
                stat.setString(5, "");
                stat.setString(6, "2021-02-01 00:00:00");
                stat.setString(7, "2021-02-01 00:00:00");
                stat.setInt(8, 100);
                stat.setInt(9, 100);
                stat.setInt(10, i % 2);
                stat.addBatch();
            }
            stat.executeBatch();
        }
        s.stop();
        System.out.println(s.getTotalTimeSeconds());
    }
}
