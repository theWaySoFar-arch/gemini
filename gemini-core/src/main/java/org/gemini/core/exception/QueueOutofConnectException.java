package org.gemini.core.exception;

/**
 * 自定义队列连接超时异常
 */
public class QueueOutofConnectException extends Throwable{
    public QueueOutofConnectException(){
      super();
    }
    public QueueOutofConnectException(String info){
        super(info);
    }
    public QueueOutofConnectException(Throwable throwable){
        super(throwable);
    }
    public QueueOutofConnectException(String info,Throwable throwable){
        super(info,throwable);
    }
}
