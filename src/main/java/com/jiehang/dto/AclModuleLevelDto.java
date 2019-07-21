package com.jiehang.dto;

import com.google.common.collect.Lists;
import com.jiehang.model.SysAclModule;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName AclModuleLevelDto
 * @Description DataTransfer to Object
 * @Author jiehangcao
 * @Date 2019-07-14 19:23
 **/
@Data
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();


    private List<AclDto> aclList = Lists.newArrayList();

    /**
     * copy dept to dept dto
     * @param sysAclModule
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule,dto);
        return dto;
    }
}
