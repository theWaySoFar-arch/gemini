package org.gemini.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 一般运行日志
 * @date 2023/8/23 19:11
 */
@Data
@NoArgsConstructor
@Builder
public class CommonLogMessage extends BaseLogMessage {
    /**
     * 日志时间
     */
    private Long logTime;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 日志等级
     */
    private String logLevel;
    /**
     * 类名
     */
    private String className;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 日志序号
     */
    private Long seq;
    /**
     * 时间字符串(用于前端展示)
     */
    private String dateTime;
}
