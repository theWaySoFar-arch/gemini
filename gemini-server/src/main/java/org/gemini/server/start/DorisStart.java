package org.gemini.server.start;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gemini.core.constant.InitConfigConstant;
import org.gemini.server.store.KafkaLogToDB;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class DorisStart implements InitializingBean {
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Value("${gemini.model}")
    public String model;
    @Override
    public void afterPropertiesSet() throws Exception {
        if(InitConfigConstant.KAFKA_MODE_NAME.equals(model)){
            KafkaLogToDB kafkaLogToDB=new KafkaLogToDB(kafkaConsumer);
            kafkaLogToDB.beginIntoDb();
        }
    }
}
