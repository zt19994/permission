package com.permission.dao;

import com.permission.model.SysDept;

import java.util.List;

/**
 * 部门mapper
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    /**
     *  获取所有的部门列表
     * @return
     */
    List<SysDept> getAllDept();
}