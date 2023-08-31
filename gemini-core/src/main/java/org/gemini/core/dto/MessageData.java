package org.gemini.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 封装日志消息类（用于disruptor）
 * @date 2023/8/29 21:10
 */
@Data
@NoArgsConstructor
@Builder
public class MessageData {
    public String message;
    public String messageType;
    public MessageData(String message){
        this.message=message;
    }
    public MessageData(String message,String messageType){
        this.messageType=messageType;
        this.message=message;
    }
}
