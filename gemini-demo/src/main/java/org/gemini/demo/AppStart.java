package org.gemini.demo;

import ch.qos.logback.classic.LoggerContext;
import org.apache.logging.log4j.LogManager;
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
        //LoggerContext context = (LoggerContext) LogManager.getContext(false);

      //  System.setProperty("log4j.configurationFile", "path/to/your/log4j2.xml");


        SpringApplication.run(AppStart.class, args);
    }
}
