package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.param.RoleParam;
import com.permission.service.SysRoleAclService;
import com.permission.service.SysRoleService;
import com.permission.service.SysTreeService;
import com.permission.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    /**
     * 进入角色页面
     */
    @RequestMapping("/role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

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

    /**
     * 获取当前角色树
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param aclIds
     * @return
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }
}
