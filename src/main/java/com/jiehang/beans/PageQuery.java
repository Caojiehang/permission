package com.jiehang.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @ClassName PageQuery
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 19:36
 **/

public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1,message = "invalid page")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1,message = "invalid display number")
    private int pageSize = 10;
    @Setter
    private int offset;

    public int getOffset() {
        return (pageNo -1) * pageSize;
    }
}
