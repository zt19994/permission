package com.permission.controller;


import com.permission.common.ApplicationContextHelper;
import com.permission.common.JsonData;
import com.permission.dao.SysAclMapper;
import com.permission.dao.SysAclModuleMapper;
import com.permission.exception.PermissionException;
import com.permission.model.SysAclModule;
import com.permission.param.TestVo;
import com.permission.util.BeanValidator;
import com.permission.util.JsonMapper;
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
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = moduleMapper.selectByPrimaryKey(1);
        logger.info(JsonMapper.obj2String(sysAclModule));
        BeanValidator.check(testVo);
        return JsonData.success("test validate");
    }
}
