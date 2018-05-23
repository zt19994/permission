package com.permission.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * json数据返回
 * @author zt1994 2018/5/22 21:01
 */
@Getter
@Setter
public class JsonData {

    /**
     * 返回结果
     */
    private boolean ret;

    /**
     * msg 信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    private JsonData(boolean result){
        this.ret = result;
    }

    /**
     * 成功
     * @param data
     * @param msg
     * @return
     */
    public static JsonData success(Object data, String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = data;
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * 不返回消息
     * @param data
     * @return
     */
    public static JsonData success(Object data){
        JsonData jsonData = new JsonData(true);
        jsonData.data = data;
        return jsonData;
    }

    /**
     * 成功什么都不返回
     * @return
     */
    public static JsonData success(){
        return new JsonData(true);
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * toMap
     * @return
     */
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("ret",ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
