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
<%--                            <div class ="col-xs-6">--%>
<%--                                <div id ="dynamic-table_filter" class="dataTables_filter"><label></label>--%>
<%--                                    Search:--%>
<%--                                    <input type="search" class="form-control input-sm" placeholder=" ">--%>
<%--                            </div>--%>
<%--                        </div>--%>
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
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">up-level dept</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="select dept" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">name</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">sequence</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptRemark">comment</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">department</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="select dept" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">name</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">email</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">telephone</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">status</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="select status" style="width: 150px;">
                        <option value="1">valid</option>
                        <option value="0">invalid</option>
                        <option value="2">deleted</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">comment</label></td>
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
<script type="application/javascript">
    $(function () {
        var deptList; // store dept tree list
        var deptMap = {};// store department info

        var optionStr = "";
        var lastClickDeptId = -1;
        var deptListTemplate = $('#deptListTemplate').html();
        Mustache.parse(deptListTemplate);
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
            $(".dept-delete").click(function (e) {
                //
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                if(confirm("confirm deleting dept ["+ deptName +"]?")) {
                    //todo:
                    console.log("delete dept: "+ deptName);
                }
            });

            $(".dept-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                handleDeptSelected(deptId);
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
            //Todo:
            console.log("load user list, deptId:" + deptId);
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
        
    })
</script>
</body>
</html>