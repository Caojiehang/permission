package com.jiehang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName EventDto
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-26 17:08
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto  {

    private String id;

    private String title;

    private long startTime;

    private long endTime;

    private int allDay;

}
