package com.permission.controller;


import com.permission.common.JsonData;
import com.permission.exception.PermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
}
