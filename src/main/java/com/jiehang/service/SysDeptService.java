package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysDeptMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysDept;
import com.jiehang.param.DeptParam;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.IpUtil;
import com.jiehang.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DeptService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-11 00:35
 **/
@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;
    /**
     * save operation
     * @param param
     */
    public void save(DeptParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamException("The same department name has been created under this level");
        }
        SysDept dept = SysDept.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark()).build();
        /**
         * set level
         */
        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        dept.setOperator(RequestHolder.getCurrentHolder().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        /**
         * insert selective: only handle input value columns without others.
         */
        sysDeptMapper.insertSelective(dept);

    }

    /**
     * update operation
     * @param param
     */
    public void update(DeptParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamException("The same department name has been created under this level");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"the selected department to update is not existed");
        if(checkExist(param.getParentId(),param.getName(),param.getId())) {
            throw new ParamException("The same department name has been created under this level");
        }
        SysDept after = SysDept.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentHolder().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));//TODO:
        after.setOperateTime(new Date());
        updateWithChild(before,after);
    }

    /**
     * update all department table
     * @param before
     * @param after
     */
    private void updateWithChild(SysDept before,SysDept after) {
      //  sysDeptMapper.updateByPrimaryKey(after);
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if(!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if(CollectionUtils.isNotEmpty(deptList)) {
                for(SysDept dept :deptList) {
                    String level = dept.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);

    }

    /**
     * Check whether the same department has been created
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId,String deptName,Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId,deptName,deptId) > 0;
    }

    /**
     * Get current level
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept == null) {
            return null;
        }
        return dept.getLevel();
    }


}
