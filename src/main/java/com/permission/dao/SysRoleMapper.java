package com.permission.dao;

import com.permission.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色mapper
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 获取所有角色
     */
    List<SysRole> getAll();

    /**
     * 查询角色数量
     */
    int countByName(@Param("name") String name, @Param("id") Integer id);

    /**
     * 根据id列表获取角色
     *
     * @param idList
     * @return
     */
    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}