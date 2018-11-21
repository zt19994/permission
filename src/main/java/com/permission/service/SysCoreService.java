package com.permission.service;

import com.google.common.collect.Lists;
import com.permission.common.RequestHolder;
import com.permission.dao.SysAclMapper;
import com.permission.dao.SysRoleAclMapper;
import com.permission.dao.SysRoleUserMapper;
import com.permission.model.SysAcl;
import com.permission.model.SysUser;
import edu.princeton.cs.algs4.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zt1994 2018/11/1 20:31
 */
@Service
public class SysCoreService {


    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 获取当前用户权限列表
     *
     * @return
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }


    /**
     * 获取角色已分配权限列表
     *
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }


    /**
     * 获取一个用户的权限列表
     *
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);

    }

    /**
     * 是否是超级管理员
     *
     * @return
     */
    public boolean isSuperAdmin() {
        //自己定义的假的超级管理员权限，实际中需要根据项目更改
        //可以从配置文件中获取，也可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return true;
    }


    /**
     * 是否有url权限
     * @param url
     * @return
     */
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }
        List<SysAcl> userAclList = getCurrentUserAclList();
        Set<Integer> userAclIdSet  = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        boolean hasValidAcl = false;
        //规则：只要有一个权限点有权限，那么就认为有权限访问
        for (SysAcl acl : aclList) {
            //判断一个用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1) { //权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        if (!hasValidAcl){
            return true;
        }
        return false;
    }
}
