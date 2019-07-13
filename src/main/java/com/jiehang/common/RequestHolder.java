package com.jiehang.common;

import com.jiehang.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RequestHolder
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 23:12
 **/

public class RequestHolder {
    /**
     * get user holder now
     */
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();
    /**
     * get request session info
     */
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    /**
     * add operate user to holder
     * @param sysUser
     */
    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    /**
     * add request session to holder
     * @param request
     */
    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * get current user
     * @return
     */
    public static SysUser getCurrentHolder() {
        return userHolder.get();
    }

    /**
     * get current request session
     * @return
     */
    public static  HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }
    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
