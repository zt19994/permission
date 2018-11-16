package com.permission.dao;

import com.permission.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    /**
     * 根据用户id获取角色列表
     *
     * @param userId
     * @return
     */
    List<Integer> getRoleIdListByUserId(@Param("userId") int userId);

    /**
     * 根据角色id获取用户id列表
     *
     * @param roleId
     * @return
     */
    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);

    /**
     * 根据角色id删除用户
     *
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") int roleId);

    /**
     * 批量新增角色用户
     *
     * @param roleUserList
     */
    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

    /**
     * 根据角色id列表获取用户
     *
     * @param roleIdList
     * @return
     */
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}