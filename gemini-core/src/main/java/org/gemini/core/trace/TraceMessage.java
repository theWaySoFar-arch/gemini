package org.gemini.core.trace;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/27 19:09
 */
@NoArgsConstructor
@Data
public class TraceMessage {
    /**
     * 链路追踪码
     */
    private String traceId;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 位置标志
     */
    private String position;
    /**
     * 序号
     */
    private AtomicInteger serialNumber = new AtomicInteger(0);
}
