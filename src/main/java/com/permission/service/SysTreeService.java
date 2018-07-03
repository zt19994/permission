package com.permission.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.permission.dao.SysDeptMapper;
import com.permission.dto.DeptLevelDto;
import com.permission.model.SysDept;
import com.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
}
