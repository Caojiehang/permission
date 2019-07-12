package com.jiehang.controller;

import com.jiehang.common.ApplicationContextHelper;
import com.jiehang.common.JsonData;
import com.jiehang.dao.SysAclModuleMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysAclModule;
import com.jiehang.param.TestVo;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-08 02:29
 **/
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        throw new RuntimeException();
        //return JsonData.success("hello,permission!");
    }
    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
       // throw new RuntimeException();
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }

    @RequestMapping("/context.json")
    @ResponseBody
    public JsonData context(TestVo vo) throws ParamException {
        log.info("context");
        SysAclModuleMapper aclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule model = aclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(model));
        BeanValidator.check(vo);
        return JsonData.success("test context");
    }

}
