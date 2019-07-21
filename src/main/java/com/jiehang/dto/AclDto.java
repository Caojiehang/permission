package com.jiehang.dto;

import com.jiehang.model.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName AclDto
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 20:46
 **/
@Data
public class AclDto extends SysAcl {
    /**
     * default status
     */
    private boolean checked = false;
    /**
     * permission operation allowed
     */
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl,dto);
        return dto;
    }
}
