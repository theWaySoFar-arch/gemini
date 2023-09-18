package org.gemini.demo.controller;

import org.apache.trace.annotation.Trace;
import org.slf4j.LoggerFactory;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/17 12:27
 */
public class FeignService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FeignService.class);
    @Trace
    public static void hello(){
           logger.info("hello world!!!!!!!!!!!!1");
           System.out.println("hello");
    }
}
