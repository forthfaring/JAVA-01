package io.kimmking.wfy.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/3/8 11:48
 */
@Configuration
public class BeanConfiguration {
    @Bean
    public UserBean userBean() {
        return new UserBean();
    }
}
