package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.dto.DeptLevelDto;
import com.permission.param.DeptParam;
import com.permission.service.SysDeptService;
import com.permission.service.SysTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author zt1994 2018/6/13 20:57
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    private static final Logger logger = LoggerFactory.getLogger("SysDeptController");

    /**
     * 部门service
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 计算树的结构
     */
    @Autowired
    private SysTreeService sysTreeService;


    /**
     * 到部门页面
     *
     * @return
     */
    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    /**
     * 新增部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam) {
        sysDeptService.save(deptParam);
        return JsonData.success();
    }

    /**
     * 部门树
     *
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门树
     *
     * @param deptParam
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam) {
        sysDeptService.update(deptParam);
        return JsonData.success();
    }
}
