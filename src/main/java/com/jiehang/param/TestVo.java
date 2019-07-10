package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName TestVo
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-09 20:02
 **/
@Data
public class TestVo {

    /**
     * Constraint: the error msg can be defined
     * and Max,Min's value.
     */
    @NotBlank
    private String msg;

    @NotNull(message = "id can not be empty")
    @Max(value = 10,message = "the value of id should be less than 10")
    @Min(value = 0,message = "the value of id should be grater than 0")
    private Integer id;

    //@NotEmpty
    private List<String> str;


}
