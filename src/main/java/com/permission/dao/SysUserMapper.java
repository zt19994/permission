package com.permission.dao;

import com.permission.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 通过关键字查询用户
     *
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * 通过邮箱查询用户
     *
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);


    /**
     * 通过电话查询用户
     *
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

}