package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.param.AclModuleParam;
import com.permission.service.SysAclModuleService;
import com.permission.service.SysTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限模块
 *
 * @author zt1994 2018/8/14 20:32
 */
@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    private static final Logger logger = LoggerFactory.getLogger("SysAclModuleController");


    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    /**
     * 到权限模块页面
     *
     * @return
     */
    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    /**
     * 保存权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    /**
     * 更新权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    /**
     * 生成权限模块树
     *
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
