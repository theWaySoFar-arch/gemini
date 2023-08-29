package org.apache.logback.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.gemini.core.constant.DataFormatConstant;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.dto.BaseLogMessage;
import org.gemini.core.dto.CommonLogMessage;
import org.gemini.core.dto.TraceLogMessage;
import org.gemini.core.factory.TraceLogMessageFactory;
import org.gemini.core.trace.TraceMessage;
import org.gemini.core.utils.*;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe log工具类
 * @date 2023/8/27 18:58
 */
public class LogMessageUtils {


    private static final AtomicLong SEQ = new AtomicLong();
    /**
     *
     * @param iLoggingEvent
     * @param appName  服务名称
     * @param env      环境名称
     * @param runModel 运行模式
     * @return BaseLogMessage
     */
    public static BaseLogMessage getLogMessage(final ILoggingEvent iLoggingEvent, final String appName, final String env, final String runModel) {
        String formattedMessage=getMessage(iLoggingEvent);
        TraceMessage traceMessage = TTLUtils.threadLocal.get();
        //如果是trace日志
        if (formattedMessage.startsWith(MessageConstant.TRACE_PREFIX)) {
            return TraceLogMessageFactory.getTraceLogMessage(
                    traceMessage, appName, env, iLoggingEvent.getTimeStamp());
        }
        CommonLogMessage commonLogMessage=getCommonLogMessage(formattedMessage,appName,env,iLoggingEvent.getTimeStamp());
        commonLogMessage.setClassName(iLoggingEvent.getLoggerName());
        commonLogMessage.setThreadName(iLoggingEvent.getThreadName());
        commonLogMessage.setSeq(SEQ.getAndIncrement());
        StackTraceElement[] stackTraceElements=null;
        if("2".equals(runModel)) {
            //调用 getCallerData() 方法会返回一个包含调用者信息的数组。通常，这些信息包括调用者的类名、方法名、文件名和行号等。
            stackTraceElements = iLoggingEvent.getCallerData();
        }
        if(stackTraceElements!=null&&stackTraceElements.length>0) {
            StackTraceElement stackTraceElement=stackTraceElements[0];
            String method = stackTraceElement.getMethodName();
            String line = String.valueOf(stackTraceElement.getLineNumber());
            commonLogMessage.setMethod(method + "(" + stackTraceElement.getFileName() + ":" + line + ")");
        } else {
            commonLogMessage.setMethod(iLoggingEvent.getThreadName());
        }
        // dateTime字段用来保存当前服务器的时间戳字符串
        commonLogMessage.setDateTime(DateUtils.formatTimestamp(iLoggingEvent.getTimeStamp(), DataFormatConstant.FORMAT_FULL_MS));
        commonLogMessage.setLogLevel(iLoggingEvent.getLevel().toString());
        return commonLogMessage;
    }

    private static CommonLogMessage getCommonLogMessage(String formattedMessage, String appName, String env, long timeStamp) {
        CommonLogMessage commonLogMessage = CommonLogMessage.builder().logTime(timeStamp)
                .content(formattedMessage).build();
        commonLogMessage.setAppName(appName);
        commonLogMessage.setEnv(env);
        commonLogMessage.setIPaddr(IpUtils.CURRENT_IP);
        return commonLogMessage;
    }

    public static String getMessage(final ILoggingEvent iLoggingEvent) {
        // 检查日志事件的级别是否为 ERROR
        if (iLoggingEvent.getLevel().equals(Level.ERROR)) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iLoggingEvent.getThrowableProxy();
            String[] logs = new String[]{iLoggingEvent.getFormattedMessage() + '\n' + ExceptionStackTraceUtils.getStackTraceAsString(throwableProxy.getThrowable())};
            return packageMessage("{}", logs);
        } else {
            // 如果日志事件没有异常信息
            // 获取日志事件的参数数组
            Object[] args = iLoggingEvent.getArgumentArray();
            if (args != null) {
                // 遍历参数数组，如果参数是 Throwable 类型，将其转换为异常堆栈信息
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Throwable) {
                        args[i] = ExceptionStackTraceUtils.getStackTraceAsString(args[i]);
                    }
                }
                // 使用 packageMessage 方法将参数数组组装成消息字符串
                return packageMessage(iLoggingEvent.getMessage(), args);
            }
        }
        return iLoggingEvent.getFormattedMessage();
    }

    private static String packageMessage(String msg, Object[] logs) {
       if(!StringUtils.isEmpty(msg)&&msg.contains(MessageConstant.DELIM_STR)){
           //将消息模板和参数数组格式化成一个字符串
           return MessageFormatter.arrayFormat(msg,logs).getMessage();
       }
        return TraceLogMessageFactory.packageMessage(msg, logs);
    }

    public static String getLogMessage(BaseLogMessage logMessage, ILoggingEvent iLoggingEvent) {
        Map<String, String> mdc = iLoggingEvent.getMDCPropertyMap();
        Map<String, Object> map = EntityConvertUtils.entityToMap( logMessage);
        if (mdc != null) {
            map.putAll(mdc);
        }
        return JsonUtils.serialize(map);
    }
}
