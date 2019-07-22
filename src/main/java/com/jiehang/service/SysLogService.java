package com.jiehang.service;

import com.jiehang.dao.SysLogMapper;
import com.jiehang.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void saveDeptLog(SysDept before, SysDept after) {

    }

    public void saveUserLog(SysUser before,SysUser after) {

    }

    public void saveAclModuleLog(SysAclModule before,SysAclModule after) {

    }

    public void saveAclLog(SysAcl before,SysAcl after) {

    }
    public void saveRoleLog(SysRole before, SysRole after) {

    }
    public void saveRoleAclLog(int roleId, List<SysRoleAcl> before, List<SysRoleAcl> after) {

    }
    public void saveRoleUserLog(int roleId,List<SysRoleUser> before,List<SysRoleUser> after) {

    }
}

