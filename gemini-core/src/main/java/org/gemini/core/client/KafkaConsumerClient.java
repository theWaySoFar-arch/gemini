package org.gemini.core.client;


import org.gemini.core.exception.QueueOutofConnectException;

import java.util.List;

public class KafkaConsumerClient extends AbstractClient{

    @Override
    public List<String> getMessage(String topic, int size) throws QueueOutofConnectException {
        return super.getMessage(topic, size);
    }
}
