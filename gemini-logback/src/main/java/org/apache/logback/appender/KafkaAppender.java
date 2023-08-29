package org.apache.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logback.utils.LogMessageUtils;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.disruptor.LogMessageProcessor;
import org.gemini.core.disruptor.MessageSender;
import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.dto.CommonLogMessage;
import org.gemini.core.dto.MessageData;
import org.gemini.core.factory.MessageAppenderFactory;
import org.gemini.core.utils.JsonUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/27 18:39
 */
@NoArgsConstructor
@Data
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String appName;
    private String env = "default";
    private String kafkaHosts;
    private String runModel;
    private String expand;
    private KafkaProducerClient kafkaClient;
    private boolean compressor = false;
    private Disruptor<MessageData> disruptor;
    private Integer threadNum;
    private RingBuffer<MessageData>ringBuffer;
    private MessageSender messageSender;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        send(iLoggingEvent);
    }

    private void send(final ILoggingEvent iLoggingEvent) {
        //将iLoggingEvent对象封装为BaseLogMessage的数据
        BaseLogMessage logMessage= LogMessageUtils.getLogMessage(iLoggingEvent,appName,env,runModel);
        if (logMessage instanceof CommonLogMessage) {
            final String message = LogMessageUtils.getLogMessage(logMessage, iLoggingEvent);
            this.messageSender.sendCommonMessage(message);
        } else {
            this.messageSender.sendTraceMessage(JsonUtils.serialize(logMessage));
        }
    }

    @Override
    public void start() {
        super.start();
        if (this.runModel != null) {
            MessageConstant.RUN_MODEL = Integer.parseInt(this.runModel);
        }
        if (this.kafkaClient == null) {
            this.kafkaClient = KafkaProducerClient.getInstance(this.kafkaHosts, this.compressor ? "lz4" : "none");
        }
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        EventFactory<MessageData> eventFactory = MessageData::new;
        this.disruptor= new Disruptor<MessageData>(eventFactory, 1024*1024, threadFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());
        LogMessageProcessor[] consumers = new LogMessageProcessor[this.threadNum==null?10:this.threadNum];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new LogMessageProcessor(this.kafkaClient);
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        RingBuffer<MessageData>ringBuffer = disruptor.start();
        this.messageSender=new MessageSender(ringBuffer);
    }
}
