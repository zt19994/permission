package com.permission.controller;

import com.permission.beans.PageQuery;
import com.permission.common.JsonData;
import com.permission.param.SearchLogParam;
import com.permission.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限log
 *
 * @author zt1994 2018/11/27 20:05
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 进入log页面
     */
    @RequestMapping("/log.page")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    /**
     * log分页信息
     *
     * @param param
     * @param page
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page) {
        return JsonData.success(sysLogService.searchPageList(param, page));
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam("id") int id) {
        return JsonData.success();
    }


}
