package org.gemini.core.client;


import org.gemini.core.exception.QueueOutofConnectException;

public class KafkaConsumerClient extends AbstractClient{

    @Override
    public void pushMessage(final String topic, final String message) throws QueueOutofConnectException {
        super.pushMessage(topic, message);
    }
}
