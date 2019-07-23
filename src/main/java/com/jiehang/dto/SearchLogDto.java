package com.jiehang.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName SearchLogDto
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-23 16:26
 **/
@Data
public class SearchLogDto {
    private Integer type; //Log Type

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime; //// yyyy-MM-dd HH:mm:ss

    private Date toTime; //// yyyy-MM-dd HH:mm:ss
}
