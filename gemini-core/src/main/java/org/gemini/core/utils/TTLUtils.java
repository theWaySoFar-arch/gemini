package org.gemini.core.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.gemini.core.dto.TraceLogMessage;
import org.gemini.core.trace.TraceMessage;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TransmittableThreadLocal工具类
 * @date 2023/8/27 13:45
 */
public class TTLUtils {
    /**
     *  TransmittableThreadLocal 可以在不同任务之间正确传递线程本地变量，不会受到线程复用的影响。
     */
    public static ThreadLocal<TraceMessage> threadLocal = new TransmittableThreadLocal<>();

    private TTLUtils() {
        // 私有构造函数，防止类实例化
    }

    /**
     * 设置内容
     * @param context
     */
    public static void setThreadLocal(TraceMessage context) {
        threadLocal.set(context);
    }

    /**
     * 获取
     * @return
     */
    public static TraceMessage getThreadLocal() {
        return threadLocal.get();
    }

    /**
     * 移除
     */
    public static void clearThreadLocal() {
        threadLocal.remove();
    }
}
