package com.jiehang.dto;

import com.google.common.collect.Lists;
import com.jiehang.model.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName DeptLevelDto
 * @Description Data Transfer Object
 * @Author jiehangcao
 * @Date 2019-07-11 16:59
 **/
@Data
public class DeptLevelDto  extends SysDept{

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    /**
     * copy dept to dept dto
     * @param dept
     * @return
     */
    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept,dto);
        return dto;
    }
}
