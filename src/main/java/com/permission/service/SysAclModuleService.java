package com.permission.service;

import com.google.common.base.Preconditions;
import com.permission.common.RequestHolder;
import com.permission.dao.SysAclModuleMapper;
import com.permission.exception.PermissionException;
import com.permission.model.SysAclModule;
import com.permission.model.SysDept;
import com.permission.param.AclModuleParam;
import com.permission.util.BeanValidator;
import com.permission.util.IpUtil;
import com.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
    }

    /**
     * 更新权限模块
     * @param param
     */
    public void update(AclModuleParam param){
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new PermissionException("同一层级下存在相同权限");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的权限模块不存在");

        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .status(param.getStatus()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleListList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclModuleListList)) {
                for (SysAclModule sysAclModule : aclModuleListList) {
                    String level = sysAclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysAclModule.setLevel(level);
                    }
                }
                //批量更新部门列表
                sysAclModuleMapper.batchUpdateLevel(aclModuleListList);
            }
        }

        sysAclModuleMapper.updateByPrimaryKeySelective(after);
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
