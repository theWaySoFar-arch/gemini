package org.gemini.core.client;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gemini.core.exception.QueueOutofConnectException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class KafkaConsumerClient extends AbstractClient{
    private static volatile KafkaConsumerClient instance=null;

    private KafkaConsumer kafkaConsumer;

    private int batchSize = 100; // 设置每批读取的消息数量
    private long batchTimeoutMillis = 5000; // 设置每批消息的最大等待时间（毫秒）

   private static long lastBatchTimestamp = System.currentTimeMillis();

    private KafkaConsumerClient(String hosts, String groupName, int maxPullSize) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPullSize);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        kafkaConsumer = new KafkaConsumer<>(props);
    }

    public static KafkaConsumerClient getInstance(final String hosts,final String groupName,final int maxPullSize){
        if (instance == null) {
            synchronized (KafkaConsumerClient.class) {
                if (instance == null) {
                    instance = new KafkaConsumerClient(hosts, groupName, maxPullSize);
                }
            }
        }
        return instance;
    }
    @Override
    public List<String> getMessage(final String topic) throws QueueOutofConnectException {
        List<String> list = new ArrayList<>();
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(batchTimeoutMillis));

                for (ConsumerRecord<String, String> record : records) {
                    // 处理消息
                    list.add(record.value());

                    // 当达到批量大小或者超过批量等待时间时，处理消息批次
                    if (list.size() >= batchSize || (System.currentTimeMillis() - lastBatchTimestamp) >= batchTimeoutMillis) {
                        lastBatchTimestamp = System.currentTimeMillis();
                        return list;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaConsumer.close();
        }
        return list;
    }

    public KafkaConsumer getKafkaConsumer() {
        return this.kafkaConsumer;
    }
}
