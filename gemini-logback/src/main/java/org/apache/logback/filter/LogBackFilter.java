package org.apache.logback.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/30 18:48
 */
public class LogBackFilter extends Filter<ILoggingEvent> {
    //在xml中进行配置
    private String level;
    private String filterPackage;

    public void setLevel(String level) {
        this.level = level;
    }

    public void setFilterPackage(String filterPackage) {
        this.filterPackage = filterPackage;
    }
    //决定是否接受该日志
    @Override
    public FilterReply decide(ILoggingEvent event) {

        if (level != null) {
            if (!event.getLevel().toString().equalsIgnoreCase(level)) {
                return FilterReply.DENY;
            } else {
                if (event.getLoggerName().equals(filterPackage)) {
                    return FilterReply.DENY;
                }
            }
        }
        if (event.getLoggerName().equals(filterPackage)) {
            return FilterReply.DENY;
        }
        return FilterReply.ACCEPT;
    }

}
