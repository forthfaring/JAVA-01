package com.example.jdbcinsertimprove;

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
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Data
public class JdbcInsertImproveApplication {

    private static JdbcTemplate jdbcTemplate;
    private static DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext ac = SpringApplication.run(JdbcInsertImproveApplication.class, args);
        jdbcTemplate = ac.getBean(JdbcTemplate.class);
        dataSource = ac.getBean(DataSource.class);


        insertBatchJdbc();
    }

    /**
     * @throws SQLException
     */
//    private static void insertBatchSingleThread() throws SQLException {
//        //insert 100W
//        System.out.println("start insert");
//        StopWatch s = new StopWatch();
//
//        List<Object[]> var2 = new ArrayList<>();
//        StringBuilder sb = new StringBuilder("insert into t_order values");
//        for (int x = 0; x < 1; x++) {
//            for (int i = 0; i < 100000; i++) {
//                sb.append("(");
//                Object[] fields = new Object[9];
//                fields[0] = "'" + i + x * 1000 + "NO" + "'";
//                fields[1] = 10;
//                fields[2] = 1;
//                fields[3] = 1;
//                fields[4] = "''";
//                fields[5] = "'2021-02-01 00:00:00'";
//                fields[6] = "'2021-02-01 00:00:00'";
//                fields[7] = 100;
//                fields[8] = 100;
//                var2.add(fields);
//                sb.append("null");
//                sb.append(",");
//                for (int z = 0; z < fields.length; z++) {
//                    sb.append(fields[z]);
//                    if (z == fields.length - 1) {
//                        break;
//                    }
//                    sb.append(",");
//                }
//                sb.append("),");
//            }
//            s.start();
//            System.out.println(sb.toString());
////            jdbcTemplate.batchUpdate("insert into t_order values(null,?,?,?,?,?,?,?,?,?)", var2);
//            s.stop();
//        }
//        System.out.println(s.getTotalTimeSeconds());
//    }
    private static void insertBatchJdbc() throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement stat = conn.prepareStatement("insert into t_order values(null,?,?,?,?,?,?,?,?,?)");
        //insert 100W
        System.out.println("start insert");
        StopWatch s = new StopWatch();
        s.start();
        List<Object[]> var2 = new ArrayList<>();
        for (int x = 0; x < 1; x++) {
            for (int i = 0; i < 100; i++) {
                stat.setString(1, i + x * 1000 + "NO");
                stat.setInt(2, 10);
                stat.setInt(3, 1);
                stat.setInt(4, 1);
                stat.setString(5, "");
                stat.setString(6, "2021-02-01 00:00:00");
                stat.setString(7, "2021-02-01 00:00:00");
                stat.setInt(8, 100);
                stat.setInt(9, 100);
                stat.addBatch();
            }
            stat.executeBatch();
        }
        s.stop();
        System.out.println(s.getTotalTimeSeconds());
    }
}
