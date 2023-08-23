package org.gemini.core.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;

/**KafkaProducer对象池具体实现
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/23 20:54
 */
public class KafkaProducerPool extends AbstractPool{


    @Override
    public void close() {
       super.close();
    }
}
