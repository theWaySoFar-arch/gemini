package org.gemini.core.pool;

import lombok.NoArgsConstructor;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe kafka对象工厂(负责管理对象全生命周期)
 * @date 2023/8/23 20:23
 */
@NoArgsConstructor
public class KafkaProducerFactory implements PooledObjectFactory<KafkaProducer> {

    private Properties properties=new Properties();

    /**
     *
     * @param servers  kafka的broker地址（可以多个）
     * @param compressionType 压缩方式
     */
    public KafkaProducerFactory(final String servers, final String compressionType){
        this.properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        this.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        this.properties.put(ProducerConfig.ACKS_CONFIG,"0");
        //用于控制生产者发送消息的延迟时间，便于打包多个消息一起发送
        this.properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        this.properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
    }

    /**
     * 生成KafkaProducer，封装成PooledObject
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<KafkaProducer> makeObject() throws Exception {
        KafkaProducer kafkaProducer = new KafkaProducer<>(properties);
        return new DefaultPooledObject<>(kafkaProducer);
    }

    /**
     *
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(final PooledObject<KafkaProducer> pooledObject) throws Exception {
           pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(final PooledObject<KafkaProducer> pooledObject) {
        return true;
    }

    @Override
    public void activateObject(final PooledObject<KafkaProducer> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(final PooledObject<KafkaProducer> pooledObject) throws Exception {

    }
}
