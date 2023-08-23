package org.gemini.core.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;

/**KafkaProducer对象池具体实现
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/23 20:54
 */
public class KafkaProducerPool extends AbstractPool<KafkaProducer>{

    public KafkaProducerPool(final GenericObjectPoolConfig poolConfig, final String hosts, final String compressionType) {
        super(poolConfig, new KafkaProducerFactory(hosts, compressionType));
    }

    public KafkaProducerPool(final String hosts, final String compressionType) {
        super(new GenericObjectPoolConfig(), new KafkaProducerFactory(hosts, compressionType));
    }
    @Override
    public void close() {
       super.close();
    }

    /**
     *获取对象
     * @return
     */
    @Override
    public KafkaProducer getResource() {
        KafkaProducer kafkaProducer = super.getResource();
        return kafkaProducer;
    }

    /**
     *归还对象
     * @param resource
     */
    @Override
    public void returnResource(final KafkaProducer resource) {
        if (resource != null) {
            try {
                returnResource(resource);
            } catch (Exception e) {
                invalidateResource(resource);
            }
        }
    }
}
