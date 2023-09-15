package org.apache.server.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractLogToDB {
    private Logger logger = LoggerFactory.getLogger(AbstractLogToDB.class);
    public void beginIntoDb() {

    }
}
