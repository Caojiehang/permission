package com.jiehang.controller;

import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.JsonData;
import com.jiehang.model.SysUser;
import com.jiehang.param.UserParam;
import com.jiehang.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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


}
