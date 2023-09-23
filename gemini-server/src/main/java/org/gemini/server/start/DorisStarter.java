package org.gemini.server.start;

import org.gemini.server.store.CommonLogToDB;
import org.gemini.server.store.TraceLogToDB;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/11 21:35
 */
@Component
public class DorisStarter {
    @Resource
    CommonLogToDB commonLogToDB;
    @Resource
    TraceLogToDB traceLogToDB;

    @PostConstruct
    public void start(){
      commonLogToDB.beginIntoDb();
      traceLogToDB.beginIntoDb();
      System.out.println("hhhhllllooooooo");
    }
}
