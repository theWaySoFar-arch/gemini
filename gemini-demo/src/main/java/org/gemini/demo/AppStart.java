package org.gemini.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/31 20:19
 */
@SpringBootApplication
//@EnableDubbo
////@ServletComponentScan
//@EnableScheduling
@ComponentScan({"org.gemini"})
public class AppStart {
    public static void main( String[] args ){

        SpringApplication.run(AppStart.class, args);
    }
}
