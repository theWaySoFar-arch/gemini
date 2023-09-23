package org.gemini.server.dto;

import java.time.LocalDateTime;

public class LogSearchParamsDTO {

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public String appName;

    public String enviorment;

    public String level;

    public String className;

    public String traceId;

    public String context;

    public String serverName;

}
