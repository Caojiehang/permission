<%--
  Created by IntelliJ IDEA.
  User: jiehangcao
  Date: 2019-07-12
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department management</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        User Management
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            Maintain relationship between department and customer
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            Department List&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                User List&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                Display
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> records </label>
                            </div>
                     </div>
                        <div class="col-xs-6">
                            <div id = "dynamic-table_filter" class="dataTables_filter"><lable>
                                Search: </lable>
                                <input type="search" class="form-control input-sm" placeholder="username">
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Name
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Department
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Email
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Telephone
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Status
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-dept-form" style="display: none;" >
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">Department Category</label><label style="color: #be0000">*</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="select dept" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">Name</label><label style="color: #be0000">*</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">Sequence</label><label style="color: #be0000">*</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all "></td>
            </tr>
            <tr>
                <td><label for="deptRemark">Comment</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">Department</label><label style="color: #be0000">*</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="select dept" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">Name</label><label style="color: #be0000">*</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">Email</label><label style="color: #be0000">*</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">Telephone</label><label style="color: #be0000">*</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">Status</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="select status" style="width: 150px;">
                        <option value="1">valid</option>
                        <option value="0">invalid</option>
                        <option value="2">deleted</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">Comment</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>
</script>
<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="user-edit" data-id="{{id}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{mail}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}
</script>
<script type="application/javascript">
    $(function () {
        var deptList; // store dept tree list
        var deptMap = {};// store department info
        var userMap = {};//store user info
        var optionStr = "";
        var lastClickDeptId = -1;
        var deptListTemplate = $('#deptListTemplate').html();
        Mustache.parse(deptListTemplate);
        var userListTemplate = $('#userListTemplate').html();
        Mustache.parse(userListTemplate);
        loadDeptTree();
        function loadDeptTree() {
            $.ajax({
                url:"/sys/dept/tree.json",
                success : function (result) {
                    if(result.ret) {
                        deptList = result.data;
                        var rendered = Mustache.render(deptListTemplate,{deptList: result.data});
                        $("#deptList").html(rendered);
                        recursiveRenderDept(result.data);
                        bindDeptClick();
                    } else {
                        showMessage("loading department list",result.msg,false);
                    }
                }
            })
        }
        //recursion
        function recursiveRenderDept(deptList) {
            if(deptList && deptList.length >0) {
                $(deptList).each(function (i,dept) {
                    deptMap[dept.id] = dept;
                    if(dept.deptList.length >0) {
                        var  rendered = Mustache.render(deptListTemplate,{deptList:dept.deptList});
                        $("#dept_"+dept.id).append(rendered);
                        recursiveRenderDept(dept.deptList);
                    }
                })
            }
            
        }
        //Bind the department click event
        function bindDeptClick() {
            $(".dept-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                handleDeptSelected(deptId);
            });
            $(".dept-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                if (confirm("Confirm to delete[" + deptName + "]?")) {
                    $.ajax({
                        url: "/sys/dept/delete.json",
                        data: {
                            id: deptId
                        },
                        success: function (result) {
                            if (result.ret) {
                                showMessage("Delete [" + deptName + "]", "Successfully", true);
                                loadDeptTree();
                            } else {
                                showMessage("Delete[" + deptName + "]", result.msg, false);
                            }
                        }
                    });
                }
            });
            $(".dept-edit").click(function (e) {
                e.preventDefault();
                        e.stopPropagation();

                        var deptId = $(this).attr("data-id");
                        // handleDeptSelected(deptId);
                        $("#dialog-dept-form").dialog({
                            modal: true,
                            title: "edit department",
                            open: function (event,ui) {
                                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                                optionStr = "<option value=\"0\">-</option>";
                                recursiveRenderDeptSelect(deptList,1);
                                $("#deptForm")[0].reset();
                                $("#parentId").html(optionStr);
                                $("#deptId").val(deptId);
                                var targetDept = deptMap[deptId];
                                if(targetDept) {
                                    $("#parentId").val(targetDept.parentId);
                                    $("#deptName").val(targetDept.name);
                                    $("#deptSeq").val(targetDept.seq);
                                    $("#deptRemark").val(targetDept.remark);
                                }
                    },
                    buttons : {
                        "update" : function(e) {
                            e.preventDefault();
                            updateDept(false,function (data) {
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                showMessage("update department",data.msg,false);
                            })
                        },
                        "cancel": function () {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                });
            })
        }



        function handleDeptSelected(deptId) {
            //lastClickDeptId = deptId;
            if(lastClickDeptId != -1) {
                var lastDept = $("#dept_"+lastClickDeptId+" .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId= deptId;
            loadUserList(deptId);
        }

        function loadUserList(deptId) {
            var pageSize = $("#pageSize").val();
            var url = "/sys/users/page.json?deptId=" + deptId;
            var pageNo = $("#userPage .pageNo").val() || 1;
            $.ajax({
                url : url,
                data: {
                    pageSize: pageSize,
                    pageNo: pageNo
                },
                success: function (result) {
                    renderUserListAndPage(result, url);
                }
            })
        }

        function renderUserListAndPage(result, url) {
            if (result.ret) {
                if (result.data.total > 0){
                    var rendered = Mustache.render(userListTemplate, {
                        userList: result.data.data,
                        "showDeptName": function() {
                            return deptMap[this.deptId].name;
                        },
                        "showStatus": function() {
                            return this.status == 1 ? 'valid' : (this.status == 0 ? 'invalid' : 'deleted');
                        },
                        "bold": function() {
                            return function(text, render) {
                                var status = render(text);
                                if (status == 'valid') {
                                    return "<span class='label label-sm label-success'>valid</span>";
                                } else if(status == 'invalid') {
                                    return "<span class='label label-sm label-warning'>invalid</span>";
                                } else {
                                    return "<span class='label'>deleted</span>";
                                }
                            }
                        }
                    });
                    $("#userList").html(rendered);
                    bindUserClick();
                    $.each(result.data.data, function(i, user) {
                        userMap[user.id] = user;
                    })
                } else {
                    $("#userList").html('');
                }
                var pageSize = $("#pageSize").val();
                var pageNo = $("#userPage .pageNo").val() || 1;
                renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.data.length : 0, "userPage", renderUserListAndPage);
            } else {
                showMessage("Obtain user list", result.msg, false);
            }
        }
        $(".user-add").click(function () {
            $("#dialog-user-form").dialog({
                modal: true,
                title: "add new user",
                open: function (event,ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "";
                    recursiveRenderDeptSelect(deptList,1);
                    $("#userForm")[0].reset();
                    $("#deptSelectId").html(optionStr);
                },
                buttons : {
                    "add" : function(e) {
                        e.preventDefault();
                        updateUser(true,function (data) {
                            $("#dialog-user-form").dialog("close");
                            loadUserList(lastClickDeptId);
                        }, function (data) {
                            showMessage("add new user",data.msg,false);
                        })
                    },
                    "cancel": function () {
                        $("#dialog-user-form").dialog("close");
                    }
                }
            });
        });
        function bindUserClick() {
            $(".user-acl").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var userId = $(this).attr("data-id");
                $.ajax({
                    url: "/sys/users/acls.json",
                    data: {
                        userId: userId
                    },
                    type: 'POST',
                    success: function (result) {
                        if(result.ret) {
                            console.log(result);
                        } else {
                            showMessage("Obtain user permission data",result.msg,false);
                        }
                    }
                })
            });
            $(".user-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var userId = $(this).attr("data-id");
                // handleDeptSelected(deptId);
                $("#dialog-user-form").dialog({
                    modal: true,
                    title: "edit user",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = "";
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#userForm")[0].reset();
                        $("#deptSelectId").html(optionStr);
                        var targetUser = userMap[userId];
                        if (targetUser) {
                            $("#deptSelectId").val(targetUser.deptId);
                            $("#userName").val(targetUser.username);
                            $("#userMail").val(targetUser.mail);
                            $("#userTelephone").val(targetUser.telephone);
                            $("#userStatus").val(targetUser.status);
                            $("#userRemark").val(targetUser.remark);
                            $("#userId").val(targetUser.id);
                        }
                    },
                    buttons: {
                        "update": function (e) {
                            e.preventDefault();
                            updateUser(false, function (data) {
                                $("#dialog-user-form").dialog("close");
                                loadUserList(lastClickDeptId);
                            }, function (data) {
                                showMessage("update user", data.msg, false);
                            })
                        },
                        "cancel": function () {
                            $("#dialog-user-form").dialog("close");
                        }
                    }
                })
            })
        }
        $(".dept-add").click(function () {
            $("#dialog-dept-form").dialog({
                modal: true,
                title: "add new department",
                open: function (event,ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "<option value=\"0\">-</option>";
                    recursiveRenderDeptSelect(deptList,1);
                    $("#deptForm")[0].reset();
                    $("#parentId").html(optionStr);
                    },
                buttons : {
                    "add" : function(e) {
                         e.preventDefault();
                         updateDept(true,function (data) {
                             $("#dialog-dept-form").dialog("close");
                         }, function (data) {
                             showMessage("add new department",data.msg,false);
                         })
                    },
                    "cancel": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            });

        });
        function recursiveRenderDeptSelect(deptList,level) {
            level = level | 0;
            if(deptList && deptList.length>0) {
                $(deptList).each(function (i,dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if(level > 1) {
                        for(var j = 3; j<=level;j++) {
                            blank += "..";
                        }
                        blank += "「 ";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>",{id:dept.id,name: blank+dept.name});
                    if(dept.deptList && dept.deptList.length > 0) {
                        recursiveRenderDeptSelect(dept.deptList,level+1);
                    }
                })
            }
        }

        function updateDept(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        loadDeptTree();
                        if(successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        if(failCallBack) {
                            failCallBack(result);
                        }
                    }
                }
            })

        }
        function updateUser(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/users/save.json" : "/sys/users/update.json",
                data: $("#userForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        loadDeptTree();
                        if(successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        if(failCallBack) {
                            failCallBack(result);
                        }
                    }
                }
            })

        }
        
    })
</script>
</body>
</html>