package com.jiehang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jiehang.beans.LogType;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysLogMapper;
import com.jiehang.dao.SysRoleUserMapper;
import com.jiehang.dao.SysUserMapper;
import com.jiehang.model.SysLogWithBLOBs;
import com.jiehang.model.SysRoleUser;
import com.jiehang.model.SysUser;
import com.jiehang.util.IpUtil;
import com.jiehang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysRoleUserService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-18 18:57
 **/
@Service
@Slf4j
public class SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * get user list
     * @param roleId
     * @return
     */
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
       // log.info("userIdList:"+userIdList);
        List<SysUser> sysUsers = sysUserMapper.getByIdList(userIdList);
        return sysUsers;
    }


    public void changeRoleUsers(int roleId,List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if(CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId,userIdList);
        saveRoleUserLog(roleId,originUserIdList,userIdList);
    }

    /**
     * recover role and user operation
     * @param roleId
     * @param userNameList
     */
    public void recoverRoleUsers(int roleId,List<String> userNameList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        List<Integer> userIdList = sysUserMapper.getIdListByNameList(userNameList);
        if(originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if(CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId,userIdList);
        saveRoleUserLog(roleId,originUserIdList,userIdList);
    }
    /**
     * Update Role and user relationship
     * @param roleId
     * @param userIdList
     */
    @Transactional
    public void updateRoleUsers(int roleId,List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for(Integer userId: userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .operator(RequestHolder.getCurrentHolder().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date())
                    .build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }
    /**
     * save Role and user operation log
     * @param roleId
     * @param before
     * @param after
     */
    private void saveRoleUserLog(int roleId,List<Integer> before,List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        List<String> beforeList = Lists.newArrayList();
        List<String> afterList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(before)) {
            beforeList = sysUserMapper.getNameByIdList(before);
        }
        if(CollectionUtils.isNotEmpty(after)) {
            afterList = sysUserMapper.getNameByIdList(after);
        }
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(beforeList));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(afterList));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

}
