package org.gemini.demo.controller;

import org.apache.trace.annotation.Trace;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/31 20:17
 */
@RestController
public class FeginController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FeginController.class);

    @GetMapping("/test")
    @Trace
    public String testFeign() {
        logger.info("hhhhh这是一条测试的日志");
        FeignService.hello();
        return "testFeign";
    }
}

