package org.gemini.core.client;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.gemini.core.pool.KafkaProducerPool;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 封装kafka生产者
 * @date 2023/8/23 20:40
 */
public class KafkaProducerClient extends AbstractClient{
    private static KafkaProducerClient instance;
    private KafkaProducerPool kafkaProducerPool;

}
