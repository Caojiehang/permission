package com.jiehang.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName EventDto
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-26 17:08
 **/
@Data
@Builder
public class EventDto  {

    private String id;

    private String title;

    private long startTime;

    private long endTime;

    private int allDay;

}
