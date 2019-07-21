package com.jiehang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysRoleUserMapper;
import com.jiehang.dao.SysUserMapper;
import com.jiehang.model.SysRoleUser;
import com.jiehang.model.SysUser;
import com.jiehang.util.IpUtil;
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
    }
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

}
