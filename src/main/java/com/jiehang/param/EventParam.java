package com.jiehang.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;

/**
 * @ClassName EventParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-25 15:22
 **/
@Data
public class EventParam {
    /**
     * event id
     */
    @NotBlank(message = "id can not be null")
    private String id;
    /**
     * event info
     */
    @NotBlank(message = "event title can not be empty")
    @Length(min = 1, max = 200, message = "the length should be between 2 - 200")
    private String title;
    /**
     * start time
     */
    private Date starttime;
    /**
     * endTime
     */
    private Date endtime;
    /**
     * whether all day event 1: all day 0: not
     */
//    @NotNull(message = "all day type should be add")
//    @Min(value = 0,message = "invalid type")
//    @Max(value = 1,message = "invalid type")
    private Integer allday;
    /**
     * user id
     */
//    @NotNull(message = "userId can not be empty")
    private Integer userId;
}
