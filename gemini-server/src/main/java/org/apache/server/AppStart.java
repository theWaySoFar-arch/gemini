package org.apache.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 12:38
 */
@SpringBootApplication
//@EnableDubbo
////@ServletComponentScan
//@EnableScheduling
public class AppStart {
    public static void main( String[] args ){
        SpringApplication.run(AppStart.class, args);
    }
}

