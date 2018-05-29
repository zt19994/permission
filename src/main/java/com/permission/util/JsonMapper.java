package com.permission.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json转换工具
 * @author zt1994 2018/5/29 20:41
 */
public class JsonMapper {

    private static Logger logger = LoggerFactory.getLogger("JsonMapper");

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //配置 config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 对象转换为string
     * @param src
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T src){
        if (src == null){
            return null;
        }
        try{
            return src instanceof String ? (String) src: objectMapper.writeValueAsString(src);
        }catch (Exception e){
            logger.warn("parse object to String exception, error:{}", e);
            return null;
        }
    }

    /**
     * string转化为obj
     * @param src
     * @param tTypeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String src, TypeReference<T> tTypeReference){
        if (src==null || tTypeReference==null){
            return null;
        }
        try{
            return (T) (tTypeReference.getType().equals(String.class)? src:objectMapper.readValue(src, tTypeReference));
        }catch (Exception e){
            logger.warn("parse String to obj exception, String:{}TypeReference<T>:{},error:{}",src, tTypeReference, e);
            return null;
        }
    }
}
