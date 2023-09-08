package org.gemini.core.interceptor;

import org.gemini.core.trace.TraceId;
import org.gemini.core.utils.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/8 22:31
 */
public class LogTraceIdInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId=request.getHeader("traceId");
        if(StringUtils.isEmpty(traceId)){
            TraceId.set();
        }else{
            TraceId.logTraceID.set(traceId);
        }
        return true;
    }
}
