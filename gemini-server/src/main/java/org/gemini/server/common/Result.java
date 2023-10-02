package org.gemini.server.common;

import lombok.Builder;
import lombok.Data;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe 返回同一实体类
 * @date 2023/10/2 21:20
 */
@Data
@Builder
public class Result <T>{

    /**
     * 数据
     */
    public T data;
    /**
     * 信息
     */
    public String message;
    /**
     * 状态码
     */
    public Integer code;

    public Result(){

    }
    public Result success(String message, T data){
        Result result=new Result();
        result.setCode(200);
        result.setData(data);
        result.setMessage(message);
        return result;
    }
    public Result error(String message,int code){
        Result result=new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
