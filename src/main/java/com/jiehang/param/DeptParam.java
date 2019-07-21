package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @ClassName DeptParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-11 00:25
 **/

@Data
public class DeptParam {
    /**
     * department id
     */
    private Integer id;
    /**
     * department name
     */
    @NotBlank(message = "depart name can not be empty")
    @Length(max = 200,min = 2, message = "The length of department name should be between 2 and 200 words")
    private String name;
    /**
     * up-level id
     */
    private Integer parentId = 0;
    /**
     * sequence
     */
    @NotNull(message = "the display sequence can not be empty")
    private Integer seq;
    /**
     * comment
     */
    @Length(max = 150,message = "comment length can not be more than 150 characters words")
    private String remark;

}
