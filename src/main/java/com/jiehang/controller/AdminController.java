package com.jiehang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 16:09
 **/
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @RequestMapping("index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }

}
