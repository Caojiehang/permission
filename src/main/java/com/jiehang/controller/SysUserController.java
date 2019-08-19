package com.jiehang.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.JsonData;
import com.jiehang.common.RequestHolder;
import com.jiehang.dto.AclModuleLevelDto;
import com.jiehang.model.SysAcl;
import com.jiehang.model.SysUser;
import com.jiehang.param.UserParam;
import com.jiehang.service.SysRoleService;
import com.jiehang.service.SysTreeService;
import com.jiehang.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
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
    public JsonData saveUser(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("searchResult.json")
    @ResponseBody
    public JsonData searchResult(@RequestParam("userName") String userName, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByUserName(userName,pageQuery);
        return JsonData.success(result);
    }
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId) {
        Map<String,Object> map = Maps.newHashMap();
        List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.userAclTree(userId);
        List<SysAcl> aclList = Lists.newArrayList();
        for(AclModuleLevelDto dto: aclModuleLevelDtoList) {
            if(CollectionUtils.isNotEmpty(dto.getAclList())) {
                for(SysAcl acl:dto.getAclList()) {
                    aclList.add(acl);
                }
            }
        }
        map.put("username",sysUserService.getUserInfo(userId).getUsername());
        map.put("acls",aclList);
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

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("userId") int userId) {
        sysUserService.delete(userId);
        return JsonData.success();
    }
}
