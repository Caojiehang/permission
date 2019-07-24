package com.jiehang.controller;

import com.google.common.collect.Maps;
import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.JsonData;
import com.jiehang.common.RequestHolder;
import com.jiehang.model.SysUser;
import com.jiehang.param.UserParam;
import com.jiehang.service.SysRoleService;
import com.jiehang.service.SysTreeService;
import com.jiehang.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SysUserController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 15:07
 **/
@Controller
@RequestMapping("/sys/users")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleService sysRoleService;


    @RequestMapping("/noAuth.page")

    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("acls",sysTreeService.userAclTree(userId));
        map.put("roles",sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
    @RequestMapping("/home.page")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
    @RequestMapping("/profile.page")
    public ModelAndView profile() {
        return new ModelAndView("profile");
    }
    @RequestMapping("/changePassword.json")
    @ResponseBody
    public JsonData resetPassword(@RequestParam("telephone") String telephone,@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        if(!password.equals(confirmPassword) || password.isEmpty()) {
           return JsonData.fail("something error,please check input password");
        }
        sysUserService.reSetPassword(telephone,password);
        return JsonData.success();
    }
    @RequestMapping("/userInfo.json")
    @ResponseBody
    public JsonData userInfo() {
        SysUser sysUser = sysUserService.getUserInfo(RequestHolder.getCurrentHolder().getId());
        Map<String,Object> map = Maps.newHashMap();
        map.put("username",sysUser.getUsername());
        map.put("comment",sysUser.getRemark());
        map.put("email",sysUser.getMail());
        map.put("telephone",sysUser.getTelephone());
        return JsonData.success(map);
    }
}
