package org.apache.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logback.utils.LogMessageUtils;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.disruptor.RingBufferPublisher;
import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.dto.CommonLogMessage;
import org.gemini.core.dto.MessageData;
import org.gemini.core.exception.QueueOutofConnectException;
import org.gemini.core.factory.DisruptorFactory;
import org.gemini.core.utils.JsonUtils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/27 18:39
 */
@NoArgsConstructor
@Data
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String appName="app";
    private String env = "default";
    private String kafkaHosts;
    private String runModel="1";
    private String expand;
    private KafkaProducerClient kafkaClient;
    private boolean compressor = false;
    private Disruptor<MessageData> disruptor;
    private Integer threadNum;
    private RingBuffer<MessageData>ringBuffer;
    private RingBufferPublisher ringBufferPublisher;
    private ConcurrentLinkedQueue<MessageData>cacheQueue;
    private static boolean disruptorConfigured = false;
    private int max=50;
    private long time=500;
    private AtomicLong lastSendTime = new AtomicLong(0);

    @SneakyThrows
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        send(iLoggingEvent);
    }

    private void send(final ILoggingEvent iLoggingEvent) throws QueueOutofConnectException {
        //将iLoggingEvent对象封装为BaseLogMessage的数据
        MessageData messageData=new MessageData();
        BaseLogMessage logMessage= LogMessageUtils.getLogMessage(iLoggingEvent,appName,env,runModel);
        if (logMessage instanceof CommonLogMessage) {
            final String message = LogMessageUtils.getLogMessage(logMessage, iLoggingEvent);
            messageData.setMessage(message);
            messageData.setMessageType(MessageConstant.COMMON_KEY);
        } else {
            messageData.setMessage(JsonUtils.serialize(logMessage));
            messageData.setMessageType(MessageConstant.TRACE_TYPE);
        }
        cacheQueue.offer(messageData);
        if(shouldPush()){
            kafkaClient.pushBatchMessage(cacheQueue);
            lastSendTime.set(System.currentTimeMillis());
            cacheQueue.clear();
        }
    }

    private boolean shouldPush() {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastSendTime.get();
        return cacheQueue.size()>= max || currentTime - lastTime >= time;
    }

    @Override
    public void start() {
        super.start();
        if(!disruptorConfigured){
            configureDisruptorAndSender();
            disruptorConfigured=!disruptorConfigured;
        }
        this.kafkaClient=KafkaProducerClient.getInstance("192.168.190.88:9092","none");
        this.lastSendTime=new AtomicLong(0);
        this.cacheQueue=new ConcurrentLinkedQueue<>();
    }

    private void configureDisruptorAndSender() {
        DisruptorFactory disruptorFactory = new DisruptorFactory(this.appName, env, kafkaHosts, compressor, threadNum);
        this.disruptor = disruptorFactory.createDisruptor();
        this.ringBufferPublisher = disruptorFactory.createMessageSender(disruptor.getRingBuffer());
    }
}
