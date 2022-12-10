package com.miya.redisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * @className RedisDemoApplication.java
 * @author miya
 * @creatTime 2022年12月10日22:11:14
 * @version 1.0
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

}
