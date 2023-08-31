package org.gemini.core.disruptor;


import com.lmax.disruptor.WorkHandler;
import lombok.SneakyThrows;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.dto.MessageData;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/29 19:36
 */
public class LogMessageProcessor implements WorkHandler<MessageData> {
    private KafkaProducerClient kafkaProducerClient;
    public LogMessageProcessor(KafkaProducerClient client){
        this.kafkaProducerClient=client;
    }
    @SneakyThrows
    @Override
    public void onEvent( MessageData messageData) throws Exception {
        if(messageData==null){
            System.out.println("messageData is null !!!!!!!!!!!!!!!!!!");
        }
        if(messageData.getMessageType().equals(MessageConstant.COMMON_TYPE)){
             kafkaProducerClient.pushMessage(MessageConstant.COMMON_KEY,messageData.getMessage());
        }else{
            kafkaProducerClient.pushMessage(MessageConstant.TRACE_KEY, messageData.getMessage());
        }
    }
}
