package org.gemini.core.dto;


import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BaseLogMessage {
    //服务名
    public String appName;
    //IP地址
    public String IPaddr;
    //链路追踪码
    public String traceId;
    //方法名称
    public String method;
    //环境名称
    public String env;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIPaddr() {
        return IPaddr;
    }

    public void setIPaddr(String IPaddr) {
        this.IPaddr = IPaddr;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
