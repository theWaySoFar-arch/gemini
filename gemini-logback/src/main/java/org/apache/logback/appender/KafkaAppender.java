package org.apache.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logback.utils.LogMessageUtils;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.disruptor.RingBufferPublisher;
import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.dto.CommonLogMessage;
import org.gemini.core.dto.MessageData;
import org.gemini.core.factory.DisruptorFactory;
import org.gemini.core.utils.JsonUtils;

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
    private String runModel="2";
    private String expand;
    private KafkaProducerClient kafkaClient;
    private boolean compressor = false;
    private Disruptor<MessageData> disruptor;
    private Integer threadNum;
    private RingBuffer<MessageData>ringBuffer;
    private RingBufferPublisher ringBufferPublisher;

    private static boolean disruptorConfigured = false;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        send(iLoggingEvent);
    }

    private void send(final ILoggingEvent iLoggingEvent) {
        //将iLoggingEvent对象封装为BaseLogMessage的数据
        BaseLogMessage logMessage= LogMessageUtils.getLogMessage(iLoggingEvent,appName,env,runModel);
        if (logMessage instanceof CommonLogMessage) {
            final String message = LogMessageUtils.getLogMessage(logMessage, iLoggingEvent);
            this.ringBufferPublisher.sendMessage(message,MessageConstant.COMMON_TYPE);
        } else {
            this.ringBufferPublisher.sendMessage(JsonUtils.serialize(logMessage),MessageConstant.TRACE_TYPE);
        }
    }

        @Override
        public void start() {
            super.start();
            if(!disruptorConfigured){
            configureDisruptorAndSender();
             disruptorConfigured=!disruptorConfigured;
            }
        }

        private void configureDisruptorAndSender() {
            DisruptorFactory disruptorFactory = new DisruptorFactory(this.appName, env, kafkaHosts, compressor, threadNum);
            this.disruptor = disruptorFactory.createDisruptor();
            this.ringBufferPublisher = disruptorFactory.createMessageSender(disruptor.getRingBuffer());
    }
}
