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
        if(messageData.message.startsWith(MessageConstant.Common_PREFIX)){
            String result = messageData.getMessage().replaceFirst(MessageConstant.Common_PREFIX, "");
            kafkaProducerClient.pushMessage(MessageConstant.Common_KEY,result);
        }else{
            kafkaProducerClient.pushMessage(MessageConstant.Trace_KEY,messageData.message);
        }
    }
}
