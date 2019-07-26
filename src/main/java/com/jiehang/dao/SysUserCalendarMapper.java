package com.jiehang.dao;

import com.jiehang.model.SysUserCalendar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserCalendarMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserCalendar record);

    int insertSelective(SysUserCalendar record);

    SysUserCalendar selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserCalendar record);

    int updateByPrimaryKey(SysUserCalendar record);

    List<SysUserCalendar> getEventListByUserId(@Param("userId") int userId);
}