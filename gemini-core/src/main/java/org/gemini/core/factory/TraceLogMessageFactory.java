package org.gemini.core.factory;

import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.dto.TraceLogMessage;
import org.gemini.core.trace.TraceMessage;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/27 18:50
 */
public class TraceLogMessageFactory {
    /**
     * 返回格式化后的消息
     * @param message
     * @param args
     * @return
     */
    public static String packageMessage(final String message, final Object[] args) {
        StringBuilder builder = new StringBuilder(128);
        builder.append(message);
        for (Object arg : args) {
            builder.append("\n").append(arg);
        }
        return builder.toString();
    }

    /**
     * 封装成TraceLogMessage类型的数据
     * @param traceMessage
     * @param appName
     * @param env
     * @param timeStamp
     * @return
     */
    public static BaseLogMessage getTraceLogMessage(final TraceMessage traceMessage, final String appName, final String env, final long timeStamp) {
        TraceLogMessage traceLogMessage = TraceLogMessage.builder().traceTime(timeStamp)
                .position(traceMessage.getPosition())
                .messageType(traceMessage.getMessageType())
                .serialNumber(traceMessage.getSerialNumber().get())
                .build();
        traceLogMessage.setAppName(appName);
        traceLogMessage.setEnv(env);
        return traceLogMessage;
    }
}
