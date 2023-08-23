package org.gemini.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BaseMessage {
    //服务名
    private String serverName;
    //IP地址
    private String IPaddr;
    //链路追踪码
    private String traceId;
    //方法名称
    private String method;

}
