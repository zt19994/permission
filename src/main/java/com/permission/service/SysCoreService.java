package com.permission.service;

import com.google.common.collect.Lists;
import com.permission.common.RequestHolder;
import com.permission.dao.SysAclMapper;
import com.permission.dao.SysRoleAclMapper;
import com.permission.dao.SysRoleUserMapper;
import com.permission.model.SysAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return
     */
    public List<Integer> getCurrentUserAclList(){
        int userId = RequestHolder.getCurrentUser().getId();
    }


    /**
     * 获取角色已分配权限列表
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId){

    }


    /**
     * 获取一个用户的权限列表
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId){
        if(isSuperAdmin()){
            sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)){
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);

    }

    /**
     * 是否是超级管理员
     * @return
     */
    public boolean isSuperAdmin(){
        return true;
    }
}
