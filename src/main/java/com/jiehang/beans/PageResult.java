package com.jiehang.beans;


import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 19:36
 **/
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;

}
