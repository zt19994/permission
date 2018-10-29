package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.param.RoleParam;
import com.permission.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色控制层
 *
 * @author zt1994 2018/10/25 20:37
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 新增角色
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    /**
     * 更新角色
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    /**
     * 获取角色列表
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData List() {
        return JsonData.success(sysRoleService.getAll());
    }
}