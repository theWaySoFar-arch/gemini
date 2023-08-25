package org.gemini.core.pool;

import lombok.NoArgsConstructor;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;


/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/8/23 19:53
 */
@NoArgsConstructor
public abstract class AbstractPool<T> implements Closeable {
    protected GenericObjectPool<T> pool;
    /**
     * 重试次数
     */
    private int count=0;

    public AbstractPool(final GenericObjectPoolConfig poolConfig, final PooledObjectFactory<T> factory){
        poolConfig.setMinIdle(0);
        poolConfig.setMaxIdle(8);
        poolConfig.setMaxTotal(30);
        poolConfig.setMaxWaitMillis(1000);
        init(poolConfig,factory);
    }

    /**
     * 初始化对象池
     * @param poolConfig 对象池配置
     * @param factory    对象池工厂
     */
    public void init(final GenericObjectPoolConfig poolConfig, final PooledObjectFactory<T> factory){
            if (this.pool != null) {
                try {
                    closePool();
                } catch (Exception e) {
                }
            }
            this.pool = new GenericObjectPool<T>(factory, poolConfig);
    }

    /**
     * 关闭对象池
     */
    public void closePool(){
        try {
            this.pool.close();
        } catch (Exception e) {
            if(this.count<5){
                closePool();
            }
           e.printStackTrace();
        }finally {
            this.count=0;
        }
    }
    @Override
    public void close() {
        destroy();
    }

    public void destroy() {
        closePool();
    }

    /**
     * 获取资源
     * @return
     */
    public T getResource(){
        try {
            return pool.borrowObject();
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 归还资源
     * @param resource
     */
    protected void returnResource(final T resource){
      if(resource==null)return;
      try {
          this.pool.returnObject(resource);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    /**
     * 对象池是否活跃
     * @return
     */
    private boolean poolInactive() {
        return this.pool == null || this.pool.isClosed();
    }

    /**
     * 获取活跃的对象数量
     * @return
     */
    public int getNumActive() {
        if (poolInactive()) {
            return -1;
        }

        return this.pool.getNumActive();
    }

    /**
     * 获取空闲的对象数量
     * @return
     */
    public int getNumIdle() {
        if (poolInactive()) {
            return -1;
        }

        return this.pool.getNumIdle();
    }

    /**
     * 标记对象为无效。在对象被销毁时，可以调用此方法来通知对象池。
     * @param resource
     */
    protected void invalidateResource(final T resource) {
        try {
            this.pool.invalidateObject(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
