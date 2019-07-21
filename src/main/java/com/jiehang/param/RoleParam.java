package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName RoleParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 16:38
 **/
@Data
public class RoleParam {
    /**
     * Role Id
     */
    private Integer id;
    /**
     * Role name
     */
    @NotBlank(message = "Role name can not be empty")
    @Length(min = 2,max = 200,message = "the length should be between 2 and 200")
    private String name;
    /**
     * Role Type 0:admin 1: others
     */
    @Min(value = 1,message = "invalid role type")
    @Max(value = 2,message = "invalid role type")
    private Integer type = 1;
    /**
     * Role status
     */
    @NotNull(message = "role status can not be empty")
    @Min(value = 0,message = "invalid status")
    @Max(value = 1,message = "invalid status")
    private Integer status;
    /**
     * Role comment/description
     */
    @Length(min = 0,max = 200,message = "the comment length should be less than 200")
    private String remark;

}
