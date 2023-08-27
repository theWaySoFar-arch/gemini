package org.gemini.core.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 线程池工具类
 * @date 2023/8/25 20:36
 */
public class ThreadPoolUtils {
    /**
     * 根据默认配置创建线程池
     * @return
     */
    //int corePoolSize,
    // int maximumPoolSize,
    // long keepAliveTime,
    //  TimeUnit unit,
    // BlockingQueue<Runnable> workQueue
    public static ThreadPoolExecutor getThreadPool(){
      return new ThreadPoolExecutor(10,
              10,15,
                 TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),
                 new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 自定义配置创建线程池
     * @param corePoolSize
     * @param maximumPoolSize
     * @param capacity
     * @return
     */
    public static ThreadPoolExecutor getThreadPool(int corePoolSize,int maximumPoolSize,int capacity){
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,15,
                TimeUnit.SECONDS,new ArrayBlockingQueue<>(capacity),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}
