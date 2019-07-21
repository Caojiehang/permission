package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysAclMapper;
import com.jiehang.dao.SysAclModuleMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysAclModule;
import com.jiehang.param.AclModuleParam;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.IpUtil;
import com.jiehang.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysAclModuleService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-14 18:43
 **/
@Service
public class SysAclModuleService {
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * add method
     * @param param
     */
    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw  new ParamException("The permission module has been created");
        }
        SysAclModule sysAclModule = SysAclModule.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark()).build();
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        sysAclModule.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    /**
     * update method
     * @param param
     */
    public void update(AclModuleParam param){
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw  new ParamException("The permission module has been created");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"can not find the permission module");
        SysAclModule after = SysAclModule.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentHolder().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before,after);
    }

    /**
     * update whole permission module when any child update
     * @param before
     * @param after
     */
    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if(!after.getLevel().equals(before.getLevel())) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(curLevel + "%");
            if(CollectionUtils.isNotEmpty(aclModuleList)) {
                for(SysAclModule aclModule :aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0)  {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
       sysAclModuleMapper.updateByPrimaryKeySelective(after);

    }

    /**
     * check the existed
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    private boolean checkExist(Integer parentId,String aclModuleName,Integer aclModuleId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId,aclModuleName,aclModuleId) > 0;
    }

    /**
     * get current level
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if(sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

    /**
     * delete permission module
     * @param aclModuleId
     */
    public void delete(int aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(sysAclModule,"Permission module is not existed");
        if(sysAclModuleMapper.countByParentId(sysAclModule.getId()) > 0) {
            throw new ParamException("Sub module is existed, delete failed");
        }
        if(sysAclMapper.countByAclModuleId(sysAclModule.getId()) > 0 ) {
            throw new ParamException("The module has permissions, delete failed");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }

}
