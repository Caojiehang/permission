package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName AclParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-17 14:10
 **/
@Data
public class AclParam {
    /**
     * permission point id
     */
    private Integer id;
    /**
     * permission point name
     */
    @NotBlank(message = "permission name can not be empty")
    @Length(min = 2,max = 200,message = "the length should be between 2 and 200")
    private String name;
    /**
     * permission module id
     */
    @NotNull(message = "permission module id can not be empty")
    private Integer aclModuleId;
    /**
     * Permission url used for interception
     */
    @Length(min = 6,max = 100,message = "url length should be less than 256")
    private String url;
    /**
     * permission type : menu: 1, button:2, others:3
     */
    @NotNull(message = "type can not be empty")
    @Min(value = 1,message = "invalid type")
    @Max(value = 3,message = "invalid type")
    private Integer type;
    /**
     * permission status: 1 normal,0 frozen
     */
    @NotNull(message = "status can not be empty")
    @Min(value = 0,message = "invalid status")
    @Max(value = 1,message = "invalid status")
    private Integer status;
    /**
     * permission display sequence
     */
    @NotNull(message = "the sequence can not be empty")
    private Integer seq;
    /**
     * comment /description
     */
    @Length(min = 0,max = 256,message = "the length should be less than 256" )
    private String remark;

}
