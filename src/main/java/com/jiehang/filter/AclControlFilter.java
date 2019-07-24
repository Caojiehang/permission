package com.jiehang.filter;


import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.jiehang.common.ApplicationContextHelper;
import com.jiehang.common.JsonData;
import com.jiehang.common.RequestHolder;
import com.jiehang.model.SysUser;
import com.jiehang.service.SysCoreService;
import com.jiehang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName AclControlFilter
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-21 18:56
 **/
@Slf4j
public class AclControlFilter implements Filter {

    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private final static String noAuthUrl = "/sys/users/noAuth.page";

    /**
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map requestMap  = request.getParameterMap();
        if(exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        SysUser sysUser = RequestHolder.getCurrentHolder();
        if(sysUser == null) {
            log.info("some one visit {},but no login, parameter:{}",servletPath, JsonMapper.obj2String(requestMap));
            noAuth(request,response);
            return;
        }
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if(!sysCoreService.hasUrlAcl(servletPath)) {
            log.info("{} visit {},but no login, parameter:{}",JsonMapper.obj2String(sysUser),servletPath,JsonMapper.obj2String(requestMap));
            noAuth(request,response);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void noAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
       String servletPath = request.getServletPath();
       if(servletPath.endsWith(".json")) {
           JsonData jsonData = JsonData.fail("No accessing permission, please contact with admin");
           response.setHeader("Content-Type","application/json");
           response.getWriter().print(JsonMapper.obj2String(jsonData));
           return;
       } else {
          clientRedirect(noAuthUrl,response);
          return;
       }
    }

    /**
     *
     * @param url
     * @param response
     * @throws IOException
     */
    private void clientRedirect(String url,HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type","text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>loading...</title>\n" + "</head>\n" + "<body>\n" + "loading，please wait...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
