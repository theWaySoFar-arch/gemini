package org.gemini.server.entity;

import lombok.Data;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 查询条件
 * @date 2023/10/2 21:12
 */
@Data
public class QueryParam {
    /**
     * 开始时间
     */
  public String startTime;
    /**
     * 结束时间
     */
    public String endTime;
    /**
     * 链路追踪码
     */
  public String traceId;
    /**
     * 日志等级
     */
  public String logLevel;
    /**
     * 方法名称
     */
  public String  method ;

    /**
     *  应用名称
     */
   public String appName;
    /**
     * 类名称
     */
   public String className;
    /**
     * 环境名称
     */
   public String  env ;
    /**
     * 内容
      */

    public String  content ;



}
