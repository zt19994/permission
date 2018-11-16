package com.permission.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.permission.dao.SysAclMapper;
import com.permission.dao.SysAclModuleMapper;
import com.permission.dao.SysDeptMapper;
import com.permission.dto.AclDto;
import com.permission.dto.AclModuleLevelDto;
import com.permission.dto.DeptLevelDto;
import com.permission.model.SysAcl;
import com.permission.model.SysAclModule;
import com.permission.model.SysDept;
import com.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zt1994 2018/6/27 20:06
 * 计算树的结构
 */
@Service
public class SysTreeService {

    /**
     * 部门mapper
     */
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 权限模块mapper
     */
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    /**
     * 核心mapper
     */
    @Autowired
    private SysCoreService sysCoreService;

    /**
     * 权限mapper
     */
    @Autowired
    private SysAclMapper sysAclMapper;


    /**
     * 获取用户分配的权限
     *
     * @param userId
     * @return
     */
    public List<AclModuleLevelDto> userAclTree(int userId) {
        //获取指定用户的权限列表
        List<SysAcl> userAclList = sysCoreService.getRoleAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : userAclList) {
            AclDto dto = AclDto.adapt(sysAcl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * 角色树
     *
     * @param roleId
     * @return
     */
    public List<AclModuleLevelDto> roleTree(int roleId) {
        //1. 当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //2. 当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        //3. 系统所有的权限点
        List<AclDto> aclDtoList = Lists.newArrayList();

        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        //获取所有权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        for (SysAcl sysAcl : allAclList) {
            AclDto dto = AclDto.adapt(sysAcl);
            if (userAclIdSet.contains(sysAcl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclSet.contains(sysAcl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * 将权限列表转换为Tree
     *
     * @param aclDtoList
     * @return
     */
    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        //权限模块列表
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto aclDto : aclDtoList) {
            if (aclDto.getStatus() == 1) {
                moduleIdAclMap.put(aclDto.getAclModuleId(), aclDto);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * 绑定权限点到权限模块上
     */
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    /**
     * 权限树
     *
     * @return
     */
    public List<AclModuleLevelDto> aclModuleTree() {
        //获取所有权限模块列表
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();

        List<AclModuleLevelDto> dtoList = Lists.newArrayList();

        for (SysAclModule sysAclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(sysAclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    /**
     * 权限模块列表转化为权限模块树
     *
     * @param dtoList
     * @return
     */
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        //level -> [aclModule1, aclModule2,...]
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        //拿出一级部门
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //对rootList排序 按照seq从小到大排序
        Collections.sort(rootList, aclModuleSeqDtoComparator);
        //递归生成权限树
        transformModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }


    /**
     * 递归生成权限树
     *
     * @param dtoList
     * @param level
     * @param levelAclModuleMap
     */
    public void transformModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, aclModuleSeqDtoComparator);
                dto.setAclModuleList(tempList);
                transformModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }

    /**
     * 部门树
     *
     * @return
     */
    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept sysDept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(sysDept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     * 部门列表转化为树
     *
     * @param deptLevelList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        //level -> [dept1, dept2,...]
        Multimap<String, DeptLevelDto> levelDtoMap = ArrayListMultimap.create();
        //拿出一级部门
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : deptLevelList) {
            levelDtoMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        //对rootList排序 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        //递归生成层级树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDtoMap);
        return rootList;

    }

    /**
     * 递归生成层级树
     *
     * @param deptLevelDtoList
     * @param level
     * @param levelDtoMap
     */
    public void transformDeptTree(List<DeptLevelDto> deptLevelDtoList, String level, Multimap<String, DeptLevelDto> levelDtoMap) {
        for (int i = 0; i < deptLevelDtoList.size(); i++) {
            //遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDtoMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDtoMap);
            }
        }
    }


    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    public Comparator<AclModuleLevelDto> aclModuleSeqDtoComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     * 权限点的排序规则
     */
    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
