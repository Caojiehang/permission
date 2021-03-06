<%--
  Created by IntelliJ IDEA.
  User: jiehangcao
  Date: 2019-07-21
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <jsp:include page="/common/backend_common.jsp"/>
</head>
<body class="no-skin">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="ace-icon fa fa-home home-icon"></i>
            <a href="/sys/users/home.page">Home</a>
        </li>
        <li class="active">Dashboard</li>
    </ul>
    <!-- /.breadcrumb -->
    <div class="nav-search" id="nav-search">
        <form class="form-search">
                        <span class="input-icon">
                <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input"
                       autocomplete="off">
                <i class="ace-icon fa fa-search nav-search-icon"></i>
            </span>
        </form>
    </div>
    <!-- /.nav-search -->
</div>
<div class="page-content">
    <div class="page-header">
        <h1>
            Full Calendar
            <small>
                <i class="ace-icon fa fa-angle-double-right"></i>
                with draggable and editable events
            </small>
        </h1>
    </div>
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-sm-9">
                    <div class="space"></div>

                    <div id="calendar"></div>
                </div>
                <div class="col-sm-3">
                    <div class="widget-box transparent">
                        <div class="widget-header">
                            <h4>Draggable events</h4>
                        </div>
                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <div id="external-events">
                                    <div class="external-event label-grey" data-class="label-grey">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 1
                                    </div>

                                    <div class="external-event label-success" data-class="label-success">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 2
                                    </div>

                                    <div class="external-event label-danger" data-class="label-danger">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 3
                                    </div>

                                    <div class="external-event label-purple" data-class="label-purple">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 4
                                    </div>

                                    <div class="external-event label-yellow" data-class="label-yellow">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 5
                                    </div>

                                    <div class="external-event label-pink" data-class="label-pink">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 6
                                    </div>

                                    <div class="external-event label-info" data-class="label-info">
                                        <i class="ace-icon fa fa-arrows"></i> My Event 7
                                    </div>

                                    <label>
                                        <input type="checkbox" class="ace ace-checkbox" id="drop-remove"/>
                                        <span class="lbl"> Remove after drop</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
    $(function () {

        /* initialize the external events
            -----------------------------------------------------------------*/
         var eventMap = {};

        $('#external-events div.external-event').each(function () {

            // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
            // it doesn't need to have a start or end
            var eventObject = {
                title: $.trim($(this).text()) // use the element's text as the event title
            };

            // store the Event Object in the DOM element so we can get to it later
            $(this).data('eventObject', eventObject);

            // make the event draggable using jQuery UI
            $(this).draggable({
                zIndex: 999,
                revert: true, // will cause the event to go back to its
                revertDuration: 0 //  original position after the drag
            });

        });

        /* initialize the calendar
        -----------------------------------------------------------------*/

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        function uuid(len, radix) {
            var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
            var uuid = [], i;
            radix = radix || chars.length;

            if (len) {
                // Compact form
                for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
            } else {
                // rfc4122, version 4 form
                var r;

                // rfc4122 requires these characters
                uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
                uuid[14] = '4';

                // Fill in random data.  At i==19 set the high bits of clock sequence as
                // per rfc4122, sec. 4.1.5
                for (i = 0; i < 36; i++) {
                    if (!uuid[i]) {
                        r = 0 | Math.random()*16;
                        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                    }
                }
            }

            return uuid.join('');
        }

        var calendar = $('#calendar').fullCalendar({
            //isRTL: true,
            //firstDay: 1,// >> change first day of week
            buttonHtml: {
                prev: '<i class="ace-icon fa fa-chevron-left"></i>',
                next: '<i class="ace-icon fa fa-chevron-right"></i>'
            },

            header: {
                left: 'prev,next,today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: loadEvents(),
            // events: [{
            //     title: 'All Day Event',
            //     start: new Date(y, m, 1),
            //     className: 'label-important'
            // }, {
            //     title: 'Long Event',
            //     start: moment().subtract(5, 'days').format('YYYY-MM-DD'),
            //     end: moment().subtract(1, 'days').format('YYYY-MM-DD'),
            //     className: 'label-success'
            // }, {
            //     title: 'Some Event',
            //     start: new Date(y, m, d - 3, 16, 0),
            //     allDay: false,
            //     className: 'label-info'
            // }],

        //     eventResize: function(event, delta, revertFunc) {
		// 	alert(event.title + " end is now " + event.end.format());
		// 	var object = $(this).data('eventObject');
		// 	var objectId = object.id;
		// 	//delete original event and add new event
        //     deleteEvent(objectId);
		// 	if (!confirm("is this okay?")) {
		// 		revertFunc();
		// 	}
        //
		// },
            editable: true,
            droppable: true, // this allows things to be dropped onto the calendar !!!
            drop: function (date) { // this function is called when something is dropped

                // retrieve the dropped element's stored Event Object
                var originalEventObject = $(this).data('eventObject');
                var $extraEventClass = $(this).attr('data-class');

                // we need to copy it, so that multiple events don't have a reference to the same object
                var copiedEventObject = $.extend({}, originalEventObject);

                // assign it the date that was reported
                copiedEventObject.start = date;
                copiedEventObject.allDay = false;
                if ($extraEventClass) copiedEventObject['className'] = [$extraEventClass];

                // render the event on the calendar
                // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
               // updateEvent(true,copiedEventObject.title,copiedstart,copiedEnd,copiedEventObject.allDay);

                // is the "remove after drop" checkbox checked?
                if ($('#drop-remove').is(':checked')) {
                    // if so, remove the element from the "Draggable Events" list
                    $(this).remove();
                }
            },
            selectable: true,
            selectHelper: true,
            select: function (start, end, allDay) {
                bootbox.prompt("New Event Title:",
                    function (title) {
                    if (title !== null) {
                        var id = "Ev_"+ uuid(8,2);
                        calendar.fullCalendar('renderEvent', {
                                id: id,
                                title: title,
                                start: start,
                                end: end,
                                allDay: allDay,
                                className: 'label-info'
                            },
                            true // make the event "stick"
                        );
                        var startTime = start.format("YYYY-MM-DD HH:mm:ss");
                        var endTime = end.format("YYYY-MM-DD HH:mm:ss");
                        addEvent(id,title,startTime,endTime,true);
                    }
                });
                calendar.fullCalendar('unselect');
            },
            eventAfterRender:function(event, element, view ) {
                $(element).attr("id","event_id_"+event._id);
                },
            eventClick: function (calEvent, jsEvent, view) {

                //display a modal
                var modal =
                    '<div class="modal fade">\
          <div class="modal-dialog">\
           <div class="modal-content">\
             <div class="modal-body">\
               <button type="button" class="close" data-dismiss="modal" style="margin-top:-10px;">&times;</button>\
               <form class="no-margin">\
                  <label>Change event name &nbsp;</label>\
                  <input class="middle" autocomplete="off" type="text" value="' + calEvent.title + '" />\
					 <button type="submit" class="btn btn-sm btn-success"><i class="ace-icon fa fa-check"></i> Save</button>\
				   </form>\
				 </div>\
				 <div class="modal-footer">\
					<button type="button" class="btn btn-sm btn-danger" data-action="delete"><i class="ace-icon fa fa-trash-o"></i> Delete Event</button>\
					<button type="button" class="btn btn-sm" data-dismiss="modal"><i class="ace-icon fa fa-times"></i> Cancel</button>\
				 </div>\
			  </div>\
			 </div>\
			</div>';

                var modal = $(modal).appendTo('body');
                modal.find('form').on('submit', function (ev) {
                    ev.preventDefault();
                    calEvent.title = $(this).find("input[type=text]").val();
                    calendar.fullCalendar('updateEvent', calEvent);
                    updateEvent(calEvent._id,calEvent.title,calEvent.start.format("YYYY-MM-DD HH:mm:ss"),calEvent.end.format("YYYY-MM-DD HH:mm:ss"),true);
                    modal.modal("hide");
                });
                modal.find('button[data-action=delete]').on('click', function () {
                    calendar.fullCalendar('removeEvents', function (ev) {
                        return (ev._id == calEvent._id);
                    });
                    // var object = $(this).data('eventObject');
                    // var objectId = object._id;
                    deleteEvent(calEvent._id);
                    modal.modal("hide");
                });

                modal.modal('show').on('hidden', function () {
                    modal.remove();
                });

                //console.log(calEvent.id);
                //console.log(jsEvent);
                //console.log(view);

                // change the border color just for fun
                //$(this).css('border-color', 'red');

            }

        });

        function loadEvents() {
            $.ajax({
                url: "/sys/calendar/events.json",
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        var eventList = result.data;
                        if(eventList && eventList.length > 0) {
                            $(eventList).each(function (i,event) {
                                eventMap[event.id] = event;
                                if(event.allDay == 1) {
                                    calendar.fullCalendar("renderEvent", {
                                            id: event.id,
                                            title: event.title,
                                            start: new Date(event.startTime),
                                            end: new Date(event.endTime),
                                            allDay: true
                                        },
                                        true);
                                } else {
                                    calendar.fullCalendar("renderEvent", {
                                        id: event.id,
                                        title: event.title,
                                        start: new Date(event.startTime),
                                        end: new Date(event.endTime),
                                        allDay: false
                                    },
                                    true);
                                }
                            });
                        }
                    } else {
                        showMessage("Load user events",result.msg,false);
                    }
                }
            });
        }
        function deleteEvent(eventId) {
            $.ajax({
                url: "/sys/calendar/delete.json",
                type: 'POST',
                data: {
                    eventId: eventId
                },
                success:function (result) {
                    if(result.ret) {
                        return;
                    }else {
                        showMessage("Delete event",result.msg,false);
                    }
                }
            })

        }
        function addEvent(id,title,startTime,endTime,allday) {
            $.ajax({
                url: "/sys/calendar/save.json",
                data: {
                    id: id,
                    title: title,
                    startTime: startTime,
                    endTime: endTime,
                    allDay: allday
                },
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        showMessage("Add event","Successfully",false);
                    } else {
                        showMessage("Add event error",result.msg,false);
                    }
                }
            });

        }
        function updateEvent(id,title,startTime,endTime,allDay) {
            $.ajax({
                url: "/sys/calendar/update.json",
                data: {
                    id: id,
                    title: title,
                    startTime: startTime,
                    endTime: endTime,
                    allDay: allDay
                },
                success: function (result) {
                    if(result.ret) {
                        showMessage("Update","Successfully",false);
                    }else {
                        showMessage("Update",result.msg,false);
                    }

                }
            })
        }
    })
</script>
</body>

</html>