package org.apache.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 12:38
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.gemini"})
@MapperScan(basePackages = "org.apache.server.mapper")
public class AppStart {
    public static void main( String[] args ){
        SpringApplication.run(AppStart.class, args);
    }
}

