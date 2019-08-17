package com.jiehang.controller;

import com.jiehang.model.SysUser;
import com.jiehang.service.SysUserService;
import com.jiehang.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 15:54
 **/
@Controller
@Slf4j
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser user = sysUserService.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");

        if(StringUtils.isBlank(username)) {
            errorMsg = "username can not be empty";
        } else if(StringUtils.isBlank(password)) {
            errorMsg = "password can not be empty";
        }else if(user == null) {
            errorMsg = "user is not existed";
        } else if(!user.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "username or password error";
        } else if(user.getStatus() != 1) {
            errorMsg = "user has been frozen, please contact with admin";
        }else {
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("userName",user.getUsername());
            if(StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");//todo
            }
            return;
        }
        request.setAttribute("error",errorMsg);
        request.setAttribute("username",username);
        if(StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret",ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request,response);
    }


    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
    }
}
