package com.jiehang.beans;

/**
 * @ClassName LogType
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-22 19:57
 **/
public interface LogType {
    /**
     * operation for department
     */
    int TYPE_DEPT = 1;
    /**
     * operation for users
     */
    int TYPE_USER = 2;
    /**
     * operation for permission module
     */
    int TYPE_ACL_MODULE = 3;
    /**
     * operation for permission point
     */
    int TYPE_ACL = 4;
    /**
     * operation for role
     */
    int TYPE_ROLE = 5;
    /**
     * operation for allocating permission to role
     */
    int TYPE_ROLE_ACL = 6;
    /**
     * operation for allocation role to users
     */
    int TYPE_ROLE_USER = 7;
}
