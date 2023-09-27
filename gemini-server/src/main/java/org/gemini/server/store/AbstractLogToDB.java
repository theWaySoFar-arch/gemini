package org.gemini.server.store;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gemini.core.constant.InitConfigConstant;
import org.gemini.core.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;


public abstract class AbstractLogToDB {
    private Logger logger = LoggerFactory.getLogger(AbstractLogToDB.class);


    public abstract void beginIntoDb();

    @PostConstruct
    private void init(){

    }
}
