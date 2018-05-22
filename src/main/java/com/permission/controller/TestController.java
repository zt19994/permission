package com.permission.controller;


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

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        logger.info("hello");
        return "hello, permission";
    }
}
