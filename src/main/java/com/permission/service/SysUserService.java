package com.permission.service;

import com.google.common.base.Preconditions;
import com.permission.dao.SysUserMapper;
import com.permission.exception.ParamException;
import com.permission.model.SysUser;
import com.permission.param.UserParam;
import com.permission.util.BeanValidator;
import com.permission.util.MD5Util;
import com.permission.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zt1994 2018/7/23 20:12
 * 用户Service层
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 保存用户
     *
     * @param param
     */
    public void save(UserParam param){
        BeanValidator.check(param);
        if (CheckTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        String password = PasswordUtil.randomPassword();
        //todo
        password = "123456";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        user.setOperator("system"); //todo
        user.setOperateIp("127.0.0.1");//todo
        user.setOperateTime(new Date());
        //todo:发生email成功后
        sysUserMapper.insertSelective(user);
    }


    /**
     * 后台更新用户信息
     *
     * @param param
     */
    public void update(UserParam param) {
        BeanValidator.check(param);
        if (CheckTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        sysUserMapper.updateByPrimaryKeySelective(after);

    }

    /**
     * 校验邮箱是否存在
     *
     * @param mail
     * @param userID
     * @return
     */
    public boolean checkEmailExist(String mail, Integer userID) {
        return sysUserMapper.countByMail(mail, userID) > 0 ;
    }

    /**
     * 校验电话是否存在
     *
     * @param telephone
     * @param userId
     * @return
     */
    public boolean CheckTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }


    /**
     * 通过关键字查询用户信息
     *
     * @param keyword
     * @return
     */
    public SysUser findByKeyword(String keyword){
        return sysUserMapper.findByKeyword(keyword);
    }
}
