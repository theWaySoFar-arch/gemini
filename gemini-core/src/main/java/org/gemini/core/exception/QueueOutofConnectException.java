package org.gemini.core.exception;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 消息中间件连接异常
 * @date 2023/8/23 19:37
 */
public class QueueOutofConnectException extends Throwable{
    public QueueOutofConnectException(){
      super();
    }
    public QueueOutofConnectException(final String info){
        super(info);
    }
    public QueueOutofConnectException(final Throwable throwable){
        super(throwable);
    }
    public QueueOutofConnectException(final String info,final Throwable throwable){
        super(info,throwable);
    }
}
