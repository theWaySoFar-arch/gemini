package org.gemini.core.trace;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.gemini.core.utils.IdWorker;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 链路追踪码相关
 * @date 2023/8/27 13:56
 */
public class TraceId {
    public static TransmittableThreadLocal<String> logTraceID = new TransmittableThreadLocal<String>();
    public static IdWorker worker = new IdWorker(1, 1, 1);

    public static void set() {
        logTraceID.set(String.valueOf(worker.nextId()));
    }

    public static String getTraceId(String traceId) {
        if (traceId == null || traceId.equals("")) {
            traceId = String.valueOf(worker.nextId());
            TraceId.logTraceID.set(traceId);
        }
        return traceId;
    }
}

