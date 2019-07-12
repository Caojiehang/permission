package com.jiehang.common;

import com.jiehang.exception.ParamException;
import com.jiehang.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName SpringExceptionResolver
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-09 00:06
 **/
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    /**
     * to handler the requesting errors
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURI().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        // all requesting for json data should be end with .json
        // all requesting for page should be end with .page
        if(url.endsWith(".json")) {
            if(e instanceof PermissionException || e instanceof ParamException) {
                JsonData result = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView",result.toMap());
            } else {
                log.error("unknown json exception, url:" + url,e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",result.toMap());
            }

        } else if(url.endsWith(".page")) {
            log.error("unknown page  exception, url:" + url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",result.toMap());
        } else {
            log.error("unknown exception, url:" + url,e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}
