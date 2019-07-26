package com.jiehang.service;

import com.google.common.collect.Lists;
import com.jiehang.common.RequestHolder;
import com.jiehang.dao.SysUserCalendarMapper;
import com.jiehang.dto.EventDto;
import com.jiehang.exception.ParamException;
import com.jiehang.model.SysUser;
import com.jiehang.model.SysUserCalendar;
import com.jiehang.param.EventParam;
import com.jiehang.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysUserCalendarService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-25 15:32
 **/
@Service
@Slf4j
public class SysUserCalendarService {
    @Resource
    private SysUserCalendarMapper sysUserCalendarMapper;

    /**
     * add event
     * @param param
     */
    public void save(EventParam param) {
        BeanValidator.check(param);
        SysUser currentHolder= RequestHolder.getCurrentHolder();
        SysUserCalendar event = SysUserCalendar.builder()
                .id(param.getId())
                .title(param.getTitle())
                .starttime(param.getStarttime())
                .endtime(param.getEndtime())
                .allday(param.getAllday())
                .userId(currentHolder.getId())
                .build();
        event.setCreatetime(new Date());
        sysUserCalendarMapper.insertSelective(event);
    }

    /**
     * update event
     * @param param
     */
    public void update(EventParam param) {
        BeanValidator.check(param);
        SysUser currentHolder = RequestHolder.getCurrentHolder();
        SysUserCalendar before = sysUserCalendarMapper.selectByPrimaryKey(param.getId());
        if(before == null) {
            throw new ParamException("event is not existed");
        }
        SysUserCalendar after = SysUserCalendar.builder()
                .id(param.getId())
                .title(param.getTitle())
                .starttime(param.getStarttime())
                .endtime(param.getEndtime())
                .allday(param.getAllday())
                .userId(currentHolder.getId())
                .build();
        after.setCreatetime(new Date());
        sysUserCalendarMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * delete event
     * @param eventId
     */
    public void delete(String eventId) {
        sysUserCalendarMapper.deleteByPrimaryKey(eventId);
    }

    /**
     * getEventListByUserId
     * @return
     */
    public List<EventDto> getEventListByUserId() {
        SysUser currentHolder = RequestHolder.getCurrentHolder();
        List<EventDto> dto = Lists.newArrayList();
        List<SysUserCalendar> list = sysUserCalendarMapper.getEventListByUserId(currentHolder.getId());
        if(CollectionUtils.isNotEmpty(list)) {
            for(SysUserCalendar event: list) {
               EventDto eventDto = EventDto.builder()
                       .id(event.getId())
                       .title(event.getTitle())
                       .startTime(event.getStarttime().getTime())
                       .endTime(event.getEndtime().getTime())
                       .allDay(event.getAllday())
                       .build();
               dto.add(eventDto);
            }
            return dto;
        }
        return Lists.newArrayList();
    }
}
