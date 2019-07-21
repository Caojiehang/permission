package com.jiehang.common;

import com.jiehang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName HttpInterceptor
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-10 23:39
 **/
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";


    /**
     * before requesting
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{},param:{}",url, JsonMapper.obj2String(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME,start);
        return true;
    }

    /**
     * after correct requesting completed
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String url = request.getRequestURI().toString();
//        long start = (Long)request.getAttribute(START_TIME);
//        long end = System.currentTimeMillis();
//        Map parameterMap = request.getParameterMap();
       // log.info("request finished. url:{}, cost:{}",url,end-start);
        removeThreadLocalInfo();
    }

    /**
     * after requesting in any condition include errors occurs
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        long start = (Long)request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        //Map parameterMap = request.getParameterMap();
       // log.info("request complete. url:{}, cost:{}",url,end-start);
        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo(){
        RequestHolder.remove();
    }
}
