package org.apache.server.store;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.server.doris.StreamLoadTask;
import org.gemini.core.client.KafkaConsumerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.exception.QueueOutofConnectException;
import org.gemini.core.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CommonLogToDB extends AbstractLogToDB{

    private Logger logger = LoggerFactory.getLogger(CommonLogToDB.class);

    @Resource
    StreamLoadTask streamLoadTask;
    @Resource
    KafkaConsumerClient kafkaConsumerClient;
    public void beginIntoDb() {
        ThreadPoolUtils.getThreadPool().execute(()->{
            try {
                List<String> list = kafkaConsumerClient.getMessage(MessageConstant.COMMON_KEY);
                streamLoadTask.send(list);
            } catch (QueueOutofConnectException e) {
                logger.error(e.toString());
                throw new RuntimeException(e);
            }
        });
    }
}
