package com.jiehang.param;

import lombok.Data;

/**
 * @ClassName SearchLogParam
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-23 16:24
 **/
@Data
public class SearchLogParam {
    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime; //// yyyy-MM-dd HH:mm:ss

    private String toTime; //// yyyy-MM-dd HH:mm:ss

}
