package org.apache.trace.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 链路追踪实现类
 * @date 2023/9/8 21:51
 */
@Aspect
@Component
@ConditionalOnMissingBean(value = AbstractAspect.class, ignored = TraceAspect.class)
public class TraceAspect extends AbstractAspect{
    @Around("@annotation(org.apache.trace.annotation.Trace)")
    public Object around(JoinPoint joinPoint) throws Throwable {
        return aroundExecute(joinPoint);
    }
}
