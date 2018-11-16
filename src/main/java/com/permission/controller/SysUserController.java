package com.permission.controller;

import com.google.common.collect.Maps;
import com.permission.beans.PageQuery;
import com.permission.beans.PageResult;
import com.permission.common.JsonData;
import com.permission.model.SysUser;
import com.permission.param.UserParam;
import com.permission.service.SysRoleService;
import com.permission.service.SysTreeService;
import com.permission.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleService sysRoleService;

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


    /**
     * 根据部门id和page查询条件查询用户列表
     *
     * @param deptId
     * @param pageQuery
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

    /**
     * 获取用户权限和分配的角色
     *
     * @param userId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
}
