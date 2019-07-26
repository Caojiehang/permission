package com.jiehang.controller;

import com.jiehang.common.JsonData;
import com.jiehang.dto.EventDto;
import com.jiehang.param.EventParam;
import com.jiehang.service.SysUserCalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName SysUserCalendarController
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-25 15:31
 **/
@Controller
@RequestMapping("/sys/calendar")
public class SysUserCalendarController {
    @Resource
    private SysUserCalendarService sysUserCalendarService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(@RequestParam("id") String id,@RequestParam("title") String title,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("allDay") boolean allDay) throws ParseException {
        DateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        EventParam param = new EventParam();
        param.setId(id);
        param.setTitle(title);
        param.setStarttime(fmt2.parse(startTime));
        param.setEndtime(fmt2.parse(endTime));
        param.setAllday(allDay ? 1: 0);
        sysUserCalendarService.save(param);
        return JsonData.success();
    }
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(@RequestParam("id") String id,@RequestParam("title") String title,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("allDay") boolean allDay) throws ParseException {
        DateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        EventParam updateparam = new EventParam();
        updateparam.setId(id);
        updateparam.setStarttime(fmt2.parse(startTime));
        updateparam.setEndtime(fmt2.parse(endTime));
        updateparam.setTitle(title);
        updateparam.setAllday(allDay ? 1: 0);
        sysUserCalendarService.update(updateparam);
        return JsonData.success();
    }
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("eventId") String eventId) {
        sysUserCalendarService.delete(eventId);
        return JsonData.success();
    }
    @RequestMapping("/events.json")
    @ResponseBody
    public JsonData show() {
        List<EventDto> list = sysUserCalendarService.getEventListByUserId();
        return JsonData.success(list);
    }
}
