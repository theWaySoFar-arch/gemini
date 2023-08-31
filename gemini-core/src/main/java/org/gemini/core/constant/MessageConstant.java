package org.gemini.core.constant;


 /**
 * className：MessageConstant
 * description：日志常量
 * @author TheWaySoFar
 * @version 1.0.0
  */

public class MessageConstant {
    //链路日志前缀
     public final static String TRACE_PREFIX="TRACE:";

    //链路日志前缀
     public final static String Common_PREFIX="common:";
     /**
      * 当前链路开始标志
      */
     public final static String TRACE_START = "<";

     /**
      * 当前链路结束标志
      */
     public final static String TRACE_END = ">";

     public final static String DELIM_STR = "{}";

     public  static int RUN_MODEL= 2;
     /**
     * 普通日志存入到队列中的key
     */
     public final static String COMMON_TYPE="common_type";

     /**
     * 链路日志存入到队列中的key
     */
     public final static String TRACE_TYPE="trace_type";
     /*
      * 普通日志存入到队列中的key
     */
     public final static String COMMON_KEY="common_type";

     /**
      * 链路日志存入到队列中的key
      */
     public final static String TRACE_KEY="trace_type";

}
