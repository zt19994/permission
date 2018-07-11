package com.permission.dao;

import com.permission.model.SysDept;
import org.apache.ibatis.annotations.Param;

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
     * 获取所有的部门列表
     *
     * @return
     */
    List<SysDept> getAllDept();

    /**
     * 通过level获取子部门列表
     *
     * @param level
     * @return
     */
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    /**
     * 批量更新部门列表
     *
     * @param sysDeptList
     */
    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);

    /**
     * 通过部门名称和父类id查询总数
     *
     * @param parentId 父类id
     * @param name 部门名称
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param("parentId") int parentId, @Param("name") String name, @Param("id") Integer id);
}