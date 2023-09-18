package org.apache.server.config;



import org.gemini.core.client.KafkaConsumerClient;
import org.gemini.core.constant.InitConfigConstant;
import org.gemini.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ClientConfig {
    Logger logger= LoggerFactory.getLogger(ClientConfig.class);
    @Value("${gemini.model}")
    private static String model;
    @Value("${gemini.kafka.kafkaHosts}")
    private static String kafkaHosts;
    @Resource


    @Bean
    public KafkaConsumerClient kafkaConsumerClient(){
        if (InitConfigConstant.KAFKA_MODE_NAME.equals(model)) {
            if (StringUtils.isEmpty(kafkaHosts)) {
                logger.error("can not find kafkaHosts config! please check the application.properties(plumelog.kafka.kafkaHosts) ");
                return null;
            }
            return KafkaConsumerClient.getInstance(kafkaHosts, InitConfigConstant.KAFKA_CONSUMER_GROUP, InitConfigConstant.MAX_SEND_SIZE);
        }
        return null;
    }
}
