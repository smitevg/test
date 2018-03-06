package ru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.*;


@SpringBootApplication
@EnableScheduling
@ComponentScan("ru")
public class Application {

    static Connection conn = null;



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
