package com.permission.controller;

import com.permission.beans.PageQuery;
import com.permission.common.JsonData;
import com.permission.param.AclModuleParam;
import com.permission.param.AclParam;
import com.permission.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限
 *
 * @author zt1994 2018/8/14 20:33
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    /**
     * 保存权限模块
     *
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
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
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    /**
     * 权限列表
     *
     * @param aclModuleId
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }
}
