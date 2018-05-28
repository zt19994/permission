package com.permission.controller;


import com.permission.common.JsonData;
import com.permission.exception.PermissionException;
import com.permission.param.TestVo;
import com.permission.util.BeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * @author zt1994 2018/5/9 21:03
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static  Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        logger.info("hello");
        throw new RuntimeException("test exception");
        //return JsonData.success("hello, permission");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo testVo){
        logger.info("validate");
        try{
            Map<String,String> map = BeanValidator.validateObject(testVo);
            if(map!=null && map.entrySet().size()>0){
                for(Map.Entry<String, String> entry:map.entrySet()){
                    logger.info("{}-->{}", entry.getKey(), entry.getValue());
                }
            }
        }catch(Exception e){

        }

        return JsonData.success("test validate");
    }
}
