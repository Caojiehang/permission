package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysAclMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysAcl;
import com.jiehang.param.AclParam;
import com.jiehang.util.BeanValidator;
import com.jiehang.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysAclService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 14:08
 **/
@Service
public class SysAclService {
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private  SysLogService sysLogService;

    /**
     * save permission point
     * @param param
     */
    public void save(AclParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getAclModuleId(),param.getName(),param.getId())) {
            throw new ParamException("the permission has been existed");
        }
        SysAcl sysAcl = SysAcl.builder()
                .name(param.getName())
                .aclModuleId(param.getAclModuleId())
                .url(param.getUrl())
                .type(param.getType())
                .status(param.getStatus())
                .seq(param.getSeq())
                .remark(param.getRemark()).build();
        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());
        sysAclMapper.insertSelective(sysAcl);
        sysLogService.saveAclLog(null,sysAcl);
    }

    /**
     * update permission point
     * @param param
     */
    public void update(AclParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getAclModuleId(),param.getName(),param.getId())) {
            throw new ParamException("the permission has been existed");
        }

        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"the permission point is not existed");
        SysAcl after = SysAcl.builder()
                .id(param.getId())
                .name(param.getName())
                .aclModuleId(param.getAclModuleId())
                .url(param.getUrl())
                .type(param.getType())
                .status(param.getStatus())
                .seq(param.getSeq())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentHolder().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before,after);
    }

    /**
     *  check existed
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(int aclModuleId,String name,Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId,name,id) > 0;
    }

    /**
     * generate permission code
     * @return
     */
    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_"+(int)(Math.random()*100);
    }

    /**
     * Page display
     * @param aclModuleId
     * @param page
     * @return
     */
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if(count > 0) {
            List<SysAcl> sysAclList = sysAclMapper.getPageByAclModuleId(aclModuleId,page);
            return PageResult.<SysAcl>builder().data(sysAclList)
                    .total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

}
