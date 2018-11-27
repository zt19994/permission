package com.permission.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.permission.common.RequestHolder;
import com.permission.dao.SysRoleAclMapper;
import com.permission.model.SysRoleAcl;
import com.permission.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色权限service
 *
 * @author zt1994 2018/11/9 19:36
 */
@Service
public class SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysLogService sysLogService;

    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //获取以前的角色权限
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        //判断角色权限是否改变
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        sysLogService.saveRoleAclLog(roleId, originAclIdList, aclIdList);
    }


    /**
     * 事务管理更新角色权限
     *
     * @param roleId
     * @param aclIdList
     */
    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        //删除原有权限
        sysRoleAclMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getOperator())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }
}
