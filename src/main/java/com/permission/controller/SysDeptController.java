package com.permission.controller;

import com.permission.common.JsonData;
import com.permission.param.DeptParam;
import com.permission.service.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zt1994 2018/6/13 20:57
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    private static final Logger logger = LoggerFactory.getLogger("SysDeptController");

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 新增部门
     * @param deptParam
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){
        sysDeptService.save(deptParam);
        return JsonData.success();
    }
}
