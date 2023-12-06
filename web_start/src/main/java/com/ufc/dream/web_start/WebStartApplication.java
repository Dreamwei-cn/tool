package com.ufc.dream.web_start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.ufc.dream.web_start.mapper")
public class WebStartApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebStartApplication.class, args);
    }

}
