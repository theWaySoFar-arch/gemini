package org.gemini.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 链路日志
 * @date 2023/8/23 19:11
 */
@Data
@NoArgsConstructor
public class TraceMessage extends BaseMessage{
    /**
     * 执行的毫秒时间
     */
    private Long traceTime;

    /**
     * 当前日志在链路中所处位置 开始 or 结束
     */
    private String position;

    /**
     * 位置序号
     */
    private Integer serialNumber;
}
