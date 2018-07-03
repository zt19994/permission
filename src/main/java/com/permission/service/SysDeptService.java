package com.permission.service;

import com.permission.dao.SysDeptMapper;
import com.permission.exception.PermissionException;
import com.permission.model.SysDept;
import com.permission.param.DeptParam;
import com.permission.util.BeanValidator;
import com.permission.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zt1994 2018/6/13 21:01
 * 部门service
 */
@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new PermissionException("同一层级下存在相同部门");
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        dept.setOperator("system"); //todo
        dept.setOperateIp("127.0.0.1");//todo
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);

    }

    //todo 校验同一级下部门是否重复
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return true;
    }

    /**
     * 获取上个层级
     *
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
