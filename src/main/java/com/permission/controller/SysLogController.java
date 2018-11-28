package com.permission.controller;

import com.permission.beans.PageQuery;
import com.permission.beans.PageResult;
import com.permission.common.JsonData;
import com.permission.param.SearchLogParam;
import com.permission.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限log
 * @author zt1994 2018/11/27 20:05
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(SearchLogParam param, PageQuery page) {
        return JsonData.success();
    }


}
