package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName UserParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 15:07
 **/
@Data
public class UserParam {
    /**
     * user id
     */
    private Integer id;
    /**
     * user name
     */
    @NotBlank(message = "username can not be empty")
    @Length(min = 0,max = 20,message = "the length should be less than 20")
    private String username;
    /**
     * user phone number
     */
    @NotBlank(message = "telephone can not be empty")
    @Length(min = 1,max = 13,message = "the length should be between 1-13")
    private String telephone;
    /**
     * user email
     */
    @NotBlank(message = "email can not be empty")
    @Length(min = 5,max = 50,message = "the length should be less than 50")
    private String mail;
    /**
     * user department id
     */
    @NotNull(message = "the department id must be provided")
    private Integer deptId;
    /**
     *
     * user status
     */
    @NotNull(message = "must define status")
    @Min(value = 0,message = "invalid status")
    @Max(value = 2,message = "invalid status")
    private Integer status;
    /**
     * comment
     */
    @Length(min = 0,max = 200,message = "the length should be less than 200")
    private String remark = "";


}
