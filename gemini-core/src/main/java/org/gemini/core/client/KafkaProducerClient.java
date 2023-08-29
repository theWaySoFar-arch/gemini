package org.gemini.core.client;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.gemini.core.pool.KafkaProducerPool;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 封装kafka生产者
 * @date 2023/8/23 20:40
 */
@NoArgsConstructor
public class KafkaProducerClient extends AbstractClient{
    private static KafkaProducerClient instance;
    private KafkaProducerPool kafkaProducerPool;
    private String hosts;
    private String compressionType;
    public KafkaProducerClient(String hosts,String compressionType){
        this.hosts=hosts;
        this.compressionType=compressionType;
    }
    public static KafkaProducerClient getInstance(String hosts, String compressionType) {
          if (instance == null) {
               synchronized (KafkaProducerClient.class) {
                 if (instance == null) {
                   instance = new KafkaProducerClient(hosts, compressionType);
                 }
              }
         }
            return instance;
     }

}
