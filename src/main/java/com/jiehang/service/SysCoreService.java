package com.jiehang.service;

import com.google.common.collect.Lists;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysAclMapper;
import com.jiehang.dao.SysRoleAclMapper;
import com.jiehang.dao.SysRoleUserMapper;
import com.jiehang.model.SysAcl;
import com.jiehang.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysCoreService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 20:51
 **/
@Service
public class SysCoreService {
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * get current user permission list
     * @return
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentHolder().getId();
        return getUserAclList(userId);
    }

    /**
     * get related permission of the role selected based on roleId
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId) {
       List<Integer> aclIdList =  sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
       if(CollectionUtils.isEmpty(aclIdList)) {
           return Lists.newArrayList();
       }
       return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * get user acl list
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId) {
        if(isSupperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if(CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    /**
     * setting supper admin
     * @return
     */
    public  boolean isSupperAdmin() {
        SysUser sysUser = RequestHolder.getCurrentHolder();
        if(sysUser.getUsername().equals("Admin")) {
            return true;
        }
      return false;
    }

    /**
     * Judge whether has permission
     * 1. super admin can access all permissions
     * 2. judge user allocated permission
     * 3. only allowed to access allocated resources
     * @param url
     * @return
     */
    public boolean hasUrlAcl(String url) {
        if(isSupperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if(CollectionUtils.isEmpty(aclList)) {
            return true;
        }
        List<SysAcl> userAclIdList = getCurrentUserAclList();
        Set<Integer> userAclIdSet = userAclIdList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        boolean hasValidAcl = false;
        // regulation : one permission for accessing permission
        for(SysAcl acl: aclList) {
            //judge whether user has permission
            if(acl == null || acl.getStatus() !=1) {
                continue;
            }
            hasValidAcl = true;
            if(userAclIdSet.contains(acl.getId())) {
                return true;
            }

        }
        if(!hasValidAcl) {
            return true;
        }

        return false;
    }
}
