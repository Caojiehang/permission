package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.jiehang.beans.LogType;
import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.*;
import com.jiehang.dto.SearchLogDto;
import com.jiehang.exception.ParamException;
import com.jiehang.model.*;
import com.jiehang.param.SearchLogParam;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.IpUtil;
import com.jiehang.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysLogService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-22 20:00
 **/
@Service
public class SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclModuleMapper  sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;

    /**
     * recover operation
     * 1. check log existed?
     * 2. do with different operation type
     * 3. get old value and check whether empty
     * 4. get previous value and set it to new value
     * 5. update database
     * @param id
     */
    public void recover(int id) {
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLog,"Log is not existed");
        switch (sysLog.getType()) {
            case LogType.TYPE_DEPT:
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeDept,"Department is not existed");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("Add and delete operation can not be recovered");
                }
                SysDept afterDept = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysDept>() {
                });
                afterDept.setOperator(RequestHolder.getCurrentHolder().getUsername());
                afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterDept.setOperateTime(new Date());
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeDept,afterDept);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser,"User is not existed");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("add and delete operation can not be recovered");
                }
                SysUser afterUser = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysUser>() {
                });
                afterUser.setOperator(RequestHolder.getCurrentHolder().getUsername());
                afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperateTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser,afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule,"Permission module is not existed");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("add and delete operation can not be recovered");
                }
                SysAclModule afterAclModule = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });
                afterAclModule.setOperator(RequestHolder.getCurrentHolder().getUsername());
                afterAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperateTime(new Date());
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule,afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl,"Permission is not existed");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("Add and delete operation can not be recovered");
                }
                SysAcl afterAcl = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });
                afterAcl.setOperator(RequestHolder.getCurrentHolder().getUsername());
                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperateTime(new Date());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl,afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole,"Role is not existed");
                if(StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("add and delete operation can not be recovered");
                }
                SysRole afterRole = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });
                afterRole.setOperator(RequestHolder.getCurrentHolder().getUsername());
                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperateTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole,afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole,"Role is not existed");
                sysRoleAclService.recoverRoleAcls(sysLog.getTargetId(),JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<String>>() {
                }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole,"Role is not existed");
                sysRoleUserService.recoverRoleUsers(sysLog.getTargetId(),JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<String>>() {
                }));
                break;
                default:;
        }
    }

    /**
     * get search result page
     * @param param
     * @param pageQuery
     * @return
     */
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        SearchLogDto dto = new SearchLogDto();
        dto.setType(param.getType());
        if(StringUtils.isNotBlank(param.getBeforeSeg())) {
            dto.setBeforeSeg("%" + param.getBeforeSeg()+ "%");
        }
        if(StringUtils.isNotBlank(param.getAfterSeg())) {
            dto.setAfterSeg("%" + param.getAfterSeg() + "%");
        }
        if(StringUtils.isNotBlank(param.getOperator())) {
            dto.setOperator("%" + param.getOperator() + "%");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())) {
                dto.setFromTime(dateFormat.parse(param.getFromTime()));
            }
            if(StringUtils.isNotBlank(param.getToTime())) {
                dto.setToTime(dateFormat.parse(param.getToTime()));
            }
        }catch (Exception e) {
            throw new ParamException("input date format problem, should be like: yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySearchDto(dto);
        if(count> 0) {
            List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto,pageQuery);
            return PageResult.<SysLogWithBLOBs>builder().total(count).data(logList).build();
        }
        return  PageResult.<SysLogWithBLOBs>builder().build();
    }

    /**
     * save department operation log
     * @param before
     * @param after
     */
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * save user operation log
     * @param before
     * @param after
     */
    public void saveUserLog(SysUser before,SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * save permission module operation log
     * @param before
     * @param after
     */
    public void saveAclModuleLog(SysAclModule before,SysAclModule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);

    }

    /**
     * save permission operation log
     * @param before
     * @param after
     */
    public void saveAclLog(SysAcl before,SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);

    }

    /**
     * save role operation log
     * @param before
     * @param after
     */
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" :JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

}

