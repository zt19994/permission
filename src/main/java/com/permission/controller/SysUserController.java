package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.param.UserParam;
import com.permission.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zt1994 2018/7/19 20:08
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {


    /**
     * 用户Service层
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 新增用户
     *
     * @param userParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam userParam) {
        sysUserService.save(userParam);
        return JsonData.success();
    }

    /**
     * 更新用户
     *
     * @param userParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam userParam) {
        sysUserService.update(userParam);
        return JsonData.success();
    }
}
