package com.jiehang.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jiehang.common.JsonData;
import com.jiehang.model.SysUser;
import com.jiehang.param.RoleParam;
import com.jiehang.service.*;
import com.jiehang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 16:36
 **/
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,@RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds){
        List<Integer> aclList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId,aclList);
        return JsonData.success();
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds){
       List<Integer> userIdList = StringUtil.splitToListInt(userIds);
       sysRoleUserService.changeRoleUsers(roleId,userIdList);
       return JsonData.success();

    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("roleId") int roleId) {
        sysRoleService.delete(roleId);
        return JsonData.success();
    }

    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser ->
                sysUser.getId()).collect(Collectors.toSet());
        for(SysUser user: allUserList) {
            if(user.getStatus() == 1 && !selectedUserIdSet.contains(user.getId())) {
                unselectedUserList.add(user);
            }
        }
        //filter user
//        selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() !=1).collect(Collectors.toList());
        Map<String,List<SysUser>> map = Maps.newHashMap();
        map.put("selected",selectedUserList);
        map.put("unselected",unselectedUserList);
        return JsonData.success(map);
    }


}
