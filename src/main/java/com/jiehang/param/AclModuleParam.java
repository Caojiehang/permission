package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AclModuleParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-14 18:33
 **/
@Data
public class AclModuleParam {
    /**
     * module id
     */
    private Integer id;
    /**
     * module name
     */
    @NotBlank(message = "permission module name can not be empty")
    @Length(min = 2,max = 200,message = "the length should be between 2 and 200")
    private String name;
    /**
     * up-level id
     */
    private Integer parentId = 0;
    /**
     * module status
     * 0: frozen 1: normal
     */
    @NotNull(message = "status can not be empty")
    @Min(value = 0,message = "invalid status")
    @Max(value = 1,message = "invalid status")
    private Integer status;
    /**
     * module display sequence
     */
    @NotNull(message = "permission module seq can not be empty")
    private Integer seq;
    /**
     * module comment
     */
    @Length(max = 200,message = "the length should be less than 200")
    private String remark;

}
