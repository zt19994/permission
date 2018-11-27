package com.permission.controller;

import com.permission.beans.LogType;
import com.permission.common.RequestHolder;
import com.permission.model.*;
import com.permission.service.SysLogService;
import com.permission.util.IpUtil;
import com.permission.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author zt1994 2018/11/27 20:05
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


}
