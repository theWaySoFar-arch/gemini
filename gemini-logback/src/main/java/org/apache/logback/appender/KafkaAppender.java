package org.apache.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logback.utils.LogMessageUtils;
import org.gemini.core.client.KafkaProducerClient;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.factory.MessageAppenderFactory;

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

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        send(iLoggingEvent);
    }

    private void send(final ILoggingEvent iLoggingEvent) {
        //将iLoggingEvent对象封装为BaseLogMessage的数据
        BaseLogMessage logMessage= LogMessageUtils.getLogMessage(iLoggingEvent,appName,env,runModel);
        final String message = LogMessageUtils.getLogMessage(logMessage, iLoggingEvent);
        MessageAppenderFactory.pushMessage(message);
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
    }
}
