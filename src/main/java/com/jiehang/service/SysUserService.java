package com.jiehang.service;

import com.google.common.base.Preconditions;
import com.jiehang.beans.Mail;
import com.jiehang.beans.PageQuery;
import com.jiehang.beans.PageResult;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysUserMapper;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysUser;
import com.jiehang.param.UserParam;
import com.jiehang.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysUserService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 15:16
 **/
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * add new user
     * @param param
     */
    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("the telephone has been existed");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("the email has been existed");
        }
        String password = PasswordUtil.randomPassword();
        /**
         * password encrypted by MD5
         */
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .password(encryptedPassword)
                .deptId(param.getDeptId())
                .status(param.getStatus())
                .remark(param.getRemark()).build();
        sysUser.setOperator(RequestHolder.getCurrentHolder().getUsername());
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());
        sysUserMapper.insertSelective(sysUser);
        //todo: send email to user but will increase the response time for adding operation
//        Mail mail = new Mail();
//        mail.setSubject("Login password");
//        mail.setReceivers(Collections.singleton(sysUser.getMail()));
//        mail.setMessage(password);
//        MailUtil.send(mail);
    }

    /**
     * update user info
     * @param param
     */
    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("the telephone has been existed");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("the email has been existed");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "user is not existed");
        SysUser after = SysUser.builder().id(param.getId())
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .deptId(param.getDeptId())
                .status(param.getStatus())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentHolder().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));//TODO:
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * check email existed? by sql query count the number
     * @param email
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String email, Integer userId) {
        return sysUserMapper.countByMail(email,userId) > 0;
    }

    /**
     * check telephone existed? by sql query count the number
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0 ;
    }

    /**
     * query database to check the login user info
     * @param keyword
     * @return
     */
    public SysUser findByKeyword(String keyword) {
        return  sysUserMapper.findByKeyword(keyword);
    }

    /**
     * user list page
     * @param deptId
     * @param pageQuery
     * @return
     */
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        if(count > 0 ) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId,pageQuery);
            return PageResult.<SysUser>builder().total(count)
                    .data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}
