package com.jiehang.tag;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.jiehang.common.ApplicationContextHelper;
import com.jiehang.common.HttpInterceptor;
import com.jiehang.common.RequestHolder;
import com.jiehang.model.SysAcl;
import com.jiehang.model.SysUser;
import com.jiehang.service.SysCoreService;
import com.jiehang.util.JsonMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName AclCheckTag
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-31 22:55
 **/
@Slf4j
public class AclCheckTag extends RequestContextAwareTag {


    @Getter
    @Setter
    private String code;
    public AclCheckTag() {
        super();
    }
    @Override
    protected int doStartTagInternal() throws Exception {
        if(needShowButton()) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    private boolean needShowButton() {
        Integer currentUserId = getCurrentLoginUserId();
        if(currentUserId == null) {
            return false;
        }
        if(StringUtils.isEmpty(code)) {
            return true;
        }
        List<String> codeList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(code);
        for(String aclCode : codeList) {
            if(hasAcl(aclCode,currentUserId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * getCurrent user from session
     * @return
     */
    private Integer getCurrentLoginUserId() {
        SysUser sysUser = (SysUser) pageContext.getSession().getAttribute("user");
        return sysUser.getId();
    }

    /**
     * Permission interception for button
     * @param aclCode
     * @param userId
     * @return
     */
    private boolean hasAcl(String aclCode,int userId) {
        SysCoreService sysCoreService = ApplicationContextHelper.popBean("sysCoreService",SysCoreService.class);
        List<SysAcl> aclList = sysCoreService.getUserAclList(userId);
        if(CollectionUtils.isEmpty(aclList)) {
            return false;
        }
        Set<String> aclCodeSet = aclList.stream().map(acl->acl.getCode()).collect(Collectors.toSet());
        if(aclCodeSet.contains(aclCode)) {
            return true;
        }
        return false;
    }

    @Override
    public void release() {
       super.release();
       code = null;
    }

}
