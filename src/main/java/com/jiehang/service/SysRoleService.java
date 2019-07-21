package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysRoleAclMapper;
import com.jiehang.dao.SysRoleMapper;
import com.jiehang.dao.SysRoleUserMapper;
import com.jiehang.dao.SysUserMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysRole;
import com.jiehang.model.SysUser;
import com.jiehang.param.RoleParam;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 16:37
 **/
@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysUserMapper sysUserMapper;
    /**
     * save role / add function
     * @param param
     */
    public void save(RoleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getName(),param.getId())) {
            throw new ParamException("Role name has been existed");
        }
        SysRole role = SysRole.builder()
                .name(param.getName())
                .status(param.getStatus())
                .type(param.getType())
                .remark(param.getRemark()).build();

        role.setOperator(RequestHolder.getCurrentHolder().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);

    }

    /**
     * edit role/ update function
     * @param param
     */
    public void update(RoleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getName(),param.getId())) {
            throw new ParamException("Role name has been existed");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"Role is not existed");
        SysRole after = SysRole.builder()
                .id(param.getId())
                .name(param.getName())
                .status(param.getStatus())
                .type(param.getType())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentHolder().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * get all role list
     * @return
     */
    public List<SysRole> getAll(){
        return sysRoleMapper.getAll();
    }

    /**
     * check whether existed
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(String name,Integer id) {
        return sysRoleMapper.countByName(name,id) > 0;

    }

    /**
     * get user role info
     * @param userId
     * @return
     */
    public List<SysRole> getRoleListByUserId(int userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if(CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * grt acl role info
     * @param aclId
     * @return
     */
    public List<SysRole> getRoleListByAclId(int aclId) {
         List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if(CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * get user list by role
     * @param roleList
     * @return
     */
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if(CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if(CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

}
