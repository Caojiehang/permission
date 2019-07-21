package com.jiehang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysRoleAclMapper;
import com.jiehang.model.SysRoleAcl;
import com.jiehang.util.IpUtil;
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

    /**
     * change role and als
     * first to judge selected permission and allocated permission
     * @param roleId
     * @param aclIdList
     */
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if(originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if(CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
      updateRoleAcls(roleId,aclIdList);
    }

    /**
     * update role and acl
     * first remove allocated permission
     * then add new permissions to related role
     * @param roleId
     * @param aclIdList
     */
    @Transactional
    public void updateRoleAcls(int roleId,List<Integer> aclIdList) {
     sysRoleAclMapper.deleteByRoleId(roleId);
     if(CollectionUtils.isEmpty(aclIdList)) {
         return;
     }
     List<SysRoleAcl> roleAclList = Lists.newArrayList();
     for(Integer aclId : aclIdList) {
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

}
