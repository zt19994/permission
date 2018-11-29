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
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限log
 * @author zt1994 2018/11/27 20:05
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/log.page")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page) {
        return JsonData.success(sysLogService.searchPageList(param, page));
    }


}
