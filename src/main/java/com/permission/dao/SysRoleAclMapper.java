package com.permission.dao;

import com.permission.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    /**
     * 通过角色id列表获取权限列表
     *
     * @param roleIdList
     * @return
     */
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 根据角色id删除角色权限
     *
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") int roleId);


    /**
     * 批量新增角色权限
     *
     * @param roleAclList
     */
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);


}