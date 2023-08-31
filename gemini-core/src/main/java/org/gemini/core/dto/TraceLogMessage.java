package org.gemini.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 链路日志
 * @date 2023/8/23 19:11
 */
@NoArgsConstructor
public class TraceLogMessage extends BaseLogMessage {
    /**
     * 执行的毫秒时间
     */
    public Long traceTime;

    /**
     * 当前日志在链路中所处位置 开始 or 结束
     */
    public String position;

    /**
     * 位置序号
     */
    public Integer serialNumber;
    /**
     * 消息类型
     */
    public String messageType;

    public Long getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(Long traceTime) {
        this.traceTime = traceTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
