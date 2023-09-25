package org.gemini.server.store;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gemini.core.constant.MessageConstant;
import org.gemini.server.doris.StreamLoadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class KafkaLogToDB extends AbstractLogToDB{
    Logger logger=LoggerFactory.getLogger(KafkaLogToDB.class);
    private KafkaConsumer<String, String> kafkaConsumer;
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    StreamLoadTask streamLoadTask;

    public KafkaLogToDB(KafkaConsumer kafkaConsumer, ApplicationEventPublisher applicationEventPublisher){
        this.kafkaConsumer=kafkaConsumer;
        this.applicationEventPublisher=applicationEventPublisher;
        kafkaConsumer.subscribe(Arrays.asList());
    }


    @Override
    public void beginIntoDb() {
       poolExecutor.execute(()->{
           collectRuningLog();
       });
    }

    private void collectRuningLog() {
        while (true) {
            List<String> logList = new ArrayList();
            List<String> sendlogList = new ArrayList();
            try {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                records.forEach(record -> {

                    if (record.topic().equals(MessageConstant.COMMON_KEY)) {
                        logList.add(record.value());
                    }
                    if (record.topic().equals(MessageConstant.TRACE_KEY)) {
                        sendlogList.add(record.value());
                    }
                });
            } catch (Exception e) {
                logger.error("get logs from kafka failed! ", e);
            }
            if (logList.size() > 0) {
               // super.sendLog(super.getRunLogIndex(), logList);
                streamLoadTask.send(logList);
                //publisherMonitorEvent(logList);
            }
            if (sendlogList.size() > 0) {
                streamLoadTask.send(sendlogList);
            }
        }
    }

}
