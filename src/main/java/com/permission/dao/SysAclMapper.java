package com.permission.dao;

import com.permission.beans.PageQuery;
import com.permission.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限mapper
 */
public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    /**
     * 通过权限模块id获取总数
     *
     * @param aclModuleId
     * @return
     */
    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    /**
     * 通过权限模块id获取分页page
     *
     * @param aclModuleId
     * @param page
     * @return
     */
    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);

    /**
     * 获取权限数
     *
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);


    /**
     * 获取所有权限
     *
     * @return
     */
    List<SysAcl> getAll();

    /**
     * 通过权限id列表获取权限列表
     */
    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);
}