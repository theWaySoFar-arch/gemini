package org.gemini.server.store;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gemini.server.doris.StreamLoadTask;
import org.gemini.core.client.KafkaConsumerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.exception.QueueOutofConnectException;
import org.gemini.core.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@DependsOn("kafkaClient")
public class CommonLogToDB extends AbstractLogToDB{

    private Logger logger = LoggerFactory.getLogger(CommonLogToDB.class);

    @Resource
    StreamLoadTask streamLoadTask;
    @Resource
    KafkaConsumerClient kafkaConsumerClient;

    @Resource
    KafkaConsumer kafkaConsumer;
    public void beginIntoDb() {
        ThreadPoolUtils.getThreadPool().execute(()->{
            try {
                kafkaConsumerClient.setKafkaConsumer(kafkaConsumer);
                List<String> list = kafkaConsumerClient.getMessage(MessageConstant.COMMON_KEY);
                streamLoadTask.send(list,MessageConstant.COMMON_KEY);
            } catch (QueueOutofConnectException e) {
                logger.error(e.toString());
                throw new RuntimeException(e);
            }
        });
    }
}
