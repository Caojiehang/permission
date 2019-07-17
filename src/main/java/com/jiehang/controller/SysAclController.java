package com.jiehang.controller;

import com.jiehang.beans.PageQuery;
import com.jiehang.common.JsonData;
import com.jiehang.param.AclParam;
import com.jiehang.service.SysAclService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName SysAclController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-14 18:31
 **/
@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success( sysAclService.getPageByAclModuleId(aclModuleId,pageQuery));
    }


}
