package com.permission.dao;

import com.permission.model.SysAclModule;
import com.permission.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameAndParentId(@Param("parentId") int parentId, @Param("name") String name, @Param("id") Integer id);

    /**
     * 通过level获取子部门列表
     *
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

    /**
     * 批量更新部门列表
     *
     * @param sysAclModuleList
     */
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    /**
     * 获取所有权限
     * @return
     */
    List<SysAclModule> getAllAclModule();
}