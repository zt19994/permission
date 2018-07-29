package com.permission.controller;

import com.permission.model.SysUser;
import com.permission.service.SysUserService;
import com.permission.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zt1994 2018/7/26 20:02
 * 前台用户controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用户service
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @param request
     * @param response
     */
    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不能为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不能为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已冻结，请联系管理员";
        } else {
            //login success
            request.getSession().setAttribute("user", sysUser);
            if(StringUtils.isNotBlank(ret)){
                response.sendRedirect(ret);
            }else {
                response.sendRedirect("/admin/index.page"); //TODO
            }
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)){
            request.setAttribute("ret", ret);
        }
        String path = "signin,jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }
}
