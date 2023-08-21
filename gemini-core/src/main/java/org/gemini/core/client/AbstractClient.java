package org.gemini.core.client;

import org.gemini.core.exception.QueueOutofConnectException;

import java.util.List;

public abstract class AbstractClient{
    private static  AbstractClient client;

    public static AbstractClient getClient(){
      return client;
    }
    public void setClient(AbstractClient client){
        this.client=client;
    }
    public void pushMessage(final String topic,final String message)throws QueueOutofConnectException {
    }
    public String getMessage(final String topic)throws QueueOutofConnectException{
        return null;
    }
    public void pushBatchMessage(final String topic, final List<String> message)throws QueueOutofConnectException{

    }
    public List<String> getMessage(final String topic,final int size)throws QueueOutofConnectException{
        return null;
    }
}
