package com.lins;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lins.mapper")
public class OnlineExaminationSystemApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineExaminationSystemApiApplication.class, args);
    }

}
