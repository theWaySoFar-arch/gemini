package org.gemini.core.client;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.gemini.core.dto.MessageData;
import org.gemini.core.exception.QueueOutofConnectException;
import org.gemini.core.pool.KafkaProducerPool;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        this.kafkaProducerPool = new KafkaProducerPool(hosts, compressionType);
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

    @Override
    public void pushMessage(String topic, String message) throws QueueOutofConnectException {
        KafkaProducer kafkaProducer = null;
        try {
            kafkaProducer = kafkaProducerPool.getResource();
            kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
        } catch (Exception e) {
            throw new QueueOutofConnectException("kafka 写入失败！", e);
        } finally {
            if (kafkaProducer != null) {
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }
    }

   /* @Override
    public void putMessageList(String topic, List<String> list) throws LogQueueConnectException {
        KafkaProducer kafkaProducer = null;
        try {
            kafkaProducer = kafkaProducerPool.getResource();
            for (int a = 0; a < list.size(); a++) {
                String message = list.get(a);
                kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
            }
        } catch (Exception e) {
            throw new LogQueueConnectException("kafka 写入失败！", e);
        } finally {
            if (kafkaProducer != null) {
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }

    }*/

    @Override
    public void pushBatchMessage(String topic, List<String> message) throws QueueOutofConnectException {

    }
    public void pushBatchMessage(ConcurrentLinkedQueue<MessageData> cacheQueue)throws QueueOutofConnectException{
        KafkaProducer kafkaProducer = null;
        try {
            kafkaProducer = kafkaProducerPool.getResource();
            Iterator<MessageData> iterator = cacheQueue.iterator();
            while(iterator.hasNext()){
                MessageData data = iterator.next();
                kafkaProducer.send(new ProducerRecord<String, String>(data.getMessageType(), data.getMessage()));
            }

        } catch (Exception e) {
            throw new QueueOutofConnectException("kafka 写入失败！", e);
        } finally {
            if (kafkaProducer != null) {
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }
    }
}
