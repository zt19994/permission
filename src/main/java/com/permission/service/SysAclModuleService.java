package com.permission.service;

import com.permission.common.RequestHolder;
import com.permission.dao.SysAclModuleMapper;
import com.permission.exception.PermissionException;
import com.permission.model.SysAclModule;
import com.permission.param.AclModuleParam;
import com.permission.util.BeanValidator;
import com.permission.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 权限模块
 * @author zt1994 2018/8/15 20:31
 */
@Service
public class SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;


    /**
     * 新增权限模块
     * @param param
     */
    public void save(AclModuleParam param){
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new PermissionException("同一层级下存在相同权限");
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .status(param.getStatus()).remark(param.getRemark()).build();
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
    }

    public void update(AclModuleParam param){

    }

    public void updateWithChild(SysAclModule before, SysAclModule after){

    }

    private boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId){
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    /**
     * 获取权限模块层级
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }
}
