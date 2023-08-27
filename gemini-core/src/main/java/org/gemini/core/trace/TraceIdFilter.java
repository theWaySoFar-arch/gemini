package org.gemini.core.trace;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 参考plumelog项目实现 地址：https://gitee.com/plumeorg/plumelog?_from=gitee_search
 * @date 2023/8/27 14:12
 */
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String traceId = request.getParameter("traceId");
            if (traceId == null || "".equals(traceId)) {
                TraceId.set();
            } else {
                TraceId.logTraceID.set(traceId);
            }
        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
