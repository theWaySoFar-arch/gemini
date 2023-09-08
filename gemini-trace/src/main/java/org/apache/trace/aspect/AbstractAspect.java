package org.apache.trace.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.gemini.core.constant.MessageConstant;
import org.gemini.core.trace.TraceId;
import org.gemini.core.trace.TraceMessage;
import org.gemini.core.utils.JsonUtils;
import org.gemini.core.utils.TTLUtils;
import org.slf4j.LoggerFactory;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/8 21:51
 */
public abstract class AbstractAspect {

    private final static org.slf4j.Logger logger= LoggerFactory.getLogger(AbstractAspect.class);

    public Object aroundExecute(JoinPoint joinPoint) throws Throwable {
        TraceMessage traceMessage= TTLUtils.getThreadLocal();
        String traceId = TraceId.logTraceID.get();
        if(traceMessage==null){
            traceMessage=new TraceMessage();
            traceMessage.getSerialNumber().set(0);
        }
        //获取方法 类签名等信息
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(MessageConstant.TRACE_START);
        traceMessage.getSerialNumber().incrementAndGet();
        TTLUtils.setThreadLocal(traceMessage);
        if(traceId !=null){
            logger.info(MessageConstant.TRACE_START+ JsonUtils.serialize(traceMessage));
        }
        Object proceed=((ProceedingJoinPoint)joinPoint).proceed();
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(MessageConstant.TRACE_END);
        traceMessage.getSerialNumber().incrementAndGet();
        if(traceId!=null){
            logger.info(MessageConstant.TRACE_END+JsonUtils.serialize(traceMessage));
        }
       return  proceed;
    }
}
