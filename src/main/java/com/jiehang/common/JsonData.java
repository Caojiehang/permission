package com.jiehang.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JsonData
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-08 23:59
 **/
@Data
public class JsonData {
    /**
     * return result
     */
    private boolean ret;
    /**
     * message (specialize for error msg)
     */
    private String msg;
    /**
     * data
     */
    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    /**
     * method for success return
     * @param object
     * @param msg
     * @return
     */
    public static JsonData success(Object object,String msg) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }
    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }
    public static JsonData success() {
        return new JsonData(true);
    }

    /**
     * method for fail return
     * @param msg
     * @return
     */
    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    /**
     * return value
     * @return
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret",ret);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }
}
