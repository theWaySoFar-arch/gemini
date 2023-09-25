package org.gemini.server.config;



import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gemini.core.client.KafkaConsumerClient;
import org.gemini.core.constant.InitConfigConstant;
import org.gemini.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Configuration
@Order(1)
public class ClientConfig {
    Logger logger= LoggerFactory.getLogger(ClientConfig.class);
    @Value("${gemini.model}")
    private  String model;
    @Value("${gemini.kafka.kafkaHosts}")
    private  String kafkaHosts;



    @Bean
    public KafkaConsumer initKafkaConsumer() {
        if (InitConfigConstant.KAFKA_MODE_NAME.equals(model)) {
            if (org.springframework.util.StringUtils.isEmpty(kafkaHosts)) {
                logger.error("can not find kafkaHosts config! please check the application.properties(plumelog.kafka.kafkaHosts) ");
                return null;
            }
            return KafkaConsumerClient.getInstance(kafkaHosts, InitConfigConstant.KAFKA_CONSUMER_GROUP, InitConfigConstant.MAX_SEND_SIZE).getKafkaConsumer();
        }
        return null;
    }
}
