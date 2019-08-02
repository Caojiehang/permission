package com.jiehang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jiehang.beans.LogType;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysAclMapper;
import com.jiehang.dao.SysLogMapper;
import com.jiehang.dao.SysRoleAclMapper;
import com.jiehang.model.SysAcl;
import com.jiehang.model.SysLogWithBLOBs;
import com.jiehang.model.SysRoleAcl;
import com.jiehang.util.IpUtil;
import com.jiehang.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysRoleAclService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-18 18:57
 **/
@Service
public class SysRoleAclService {

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * change role and als
     * first to judge selected permission and allocated permission
     *
     * @param roleId
     * @param aclIdList
     */
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId,originAclIdList,aclIdList);
    }

    /**
     * recover role and user operation
     * @param roleId
     * @param aclNameList
     */
    public void recoverRoleAcls(Integer roleId,List<String> aclNameList) {
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        List<Integer> aclIdList = sysAclMapper.getIdListByNameList(aclNameList);
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId,originAclIdList,aclIdList);
    }
    /**
     * update role and acl
     * first remove allocated permission
     * then add new permissions to related role
     *
     * @param roleId
     * @param aclIdList
     */
    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl roleAcl = SysRoleAcl.builder()
                    .roleId(roleId)
                    .aclId(aclId)
                    .operator(RequestHolder.getCurrentHolder().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date())
                    .build();
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }
    /**
     * save Role and permission operation log
     * todo: now only show the aclId on the value can change to show acl Name
     * @param roleId
     * @param before
     * @param after
     */
    private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        List<String> beforeList = Lists.newArrayList();
        List<String> afterList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(before)) {
            beforeList = sysAclMapper.getNameByIdList(before);
        }
        if(CollectionUtils.isNotEmpty(after)) {
            afterList = sysAclMapper.getNameByIdList(after);
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
