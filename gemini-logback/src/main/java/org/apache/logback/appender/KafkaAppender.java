package org.apache.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logback.utils.LogMessageUtils;
import org.gemini.core.dto.BaseLogMessage;

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

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        send(iLoggingEvent);
    }

    private void send(final ILoggingEvent iLoggingEvent) {
        //将iLoggingEvent对象封装为BaseLogMessage的数据
        BaseLogMessage logMessage= LogMessageUtils.getLogMessage(iLoggingEvent,appName,env,runModel);
    }
}
