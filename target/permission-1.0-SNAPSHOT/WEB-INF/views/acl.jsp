<%--
  Created by IntelliJ IDEA.
  User: jiehangcao
  Date: 2019-07-14
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Permission</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        Permission Module Management
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            Maintain relationship between permission module and permission
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            Permission module list&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 aclModule-add"></i>
            </a>
        </div>
        <div id="aclModuleList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                Permission list&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 acl-add"></i>
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
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Permission name
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Permission module
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Permission type
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                URL
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Status
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                Sequence
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="aclList"></tbody>
                    </table>
                    <div class="row" id="aclPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-aclModule-form" style="display: none;">
    <form id="aclModuleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">up-level module</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="selcet module" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="aclModuleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleName">name</label></td>
                <td><input type="text" name="name" id="aclModuleName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleSeq">sequence</label></td>
                <td><input type="text" name="seq" id="aclModuleSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleStatus">status</label></td>
                <td>
                    <select id="aclModuleStatus" name="status" data-placeholder="select status" style="width: 150px;">
                        <option value="1">valid</option>
                        <option value="0">invalid</option>
                        <option value="2">deleted</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleRemark">comment</label></td>
                <td><textarea name="remark" id="aclModuleRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-acl-form" style="display: none;">
    <form id="aclForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">Permission module</label></td>
                <td>
                    <select id="aclModuleSelectId" name="aclModuleId" data-placeholder="select permission module" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="aclName">name</label></td>
                <input type="hidden" name="id" id="aclId"/>
                <td><input type="text" name="name" id="aclName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclType">type</label></td>
                <td>
                    <select id="aclType" name="type" data-placeholder="type" style="width: 150px;">
                        <option value="1">menu</option>
                        <option value="2">button</option>
                        <option value="3">others</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclUrl">URL</label></td>
                <td><input type="text" name="url" id="aclUrl" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclStatus">status</label></td>
                <td>
                    <select id="aclStatus" name="status" data-placeholder="select status" style="width: 150px;">
                        <option value="1">valid</option>
                        <option value="0">invalid</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclSeq">sequence</label></td>
                <td><input type="text" name="seq" id="aclSeq" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclRemark">comment</label></td>
                <td><textarea name="remark" id="aclRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="aclModuleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#aclModuleList}}
        <li class="dd-item dd2-item aclModule-name {{displayClass}}" id="aclModule_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            &nbsp;
            <a class="green {{#showDownAngle}}{{/showDownAngle}}" href="#" data-id="{{id}}" >
                <i class="ace-icon fa fa-angle-double-down bigger-120 sub-aclModule"></i>
            </a>
            <span style="float:right;">
                <a class="green aclModule-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red aclModule-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/aclModuleList}}
</ol>
</script>

<script id="aclListTemplate" type="x-tmpl-mustache">
{{#aclList}}
<tr role="row" class="acl-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="acl-edit" data-id="{{id}}">{{name}}</a></td>
    <td>{{showAclModuleName}}</td>
    <td>{{showType}}</td>
    <td>{{url}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td>
    <td>{{seq}}</td>
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green acl-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red acl-role" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/aclList}}
</script>

<script type="text/javascript">
    $(function () {
        var aclModuleList; // store alc module tree list
        var aclModuleMap = {};// store acl module info
        var aclMap = {};//store acl info
        var optionStr = "";
        var lastClickAclModuleId = -1;

        var aclModuleListTemplate = $('#aclModuleListTemplate').html();
        Mustache.parse(aclModuleListTemplate);
        var aclListTemplate = $('#aclListTemplate').html();
        Mustache.parse(aclListTemplate);

        loadAclModuleTree();

        function loadAclModuleTree() {
            $.ajax({
                url: "/sys/aclModule/tree.json",
                success : function (result) {
                    if(result.ret) {
                        //obtain data
                        aclModuleList = result.data;
                        // render data
                        var rendered = Mustache.render(aclModuleListTemplate, {
                            aclModuleList: result.data,
                            "showDownAngle": function () {
                                return function (text,render) {
                                    return (this.aclModuleList && this.aclModuleList.length > 0) ? "" : "hidden";
                                }
                            },
                            "displayClass": function () {
                                return "";
                            }
                        });
                        //setting html
                        $("#aclModuleList").html(rendered);
                        recursiveRenderAclModule(result.data);
                        bindAclModuleClick();
                    } else {
                        showMessage("loading permission module", result.msg,false);
                    }

                }
            })
        }
        $(".aclModule-add").click(function () {
            $("#dialog-aclModule-form").dialog({
                modal: true,
                title: "add new module",
                open: function (event,ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "<option value=\"0\">-</option>";
                    recursiveRenderAclModuleSelect(aclModuleList,1);
                    $("#aclModuleForm")[0].reset();
                    $("#parentId").html(optionStr);
                },
                buttons : {
                    "add" : function(e) {
                        e.preventDefault();
                        updateAclModule(true, function (data) {
                            $("#dialog-aclModule-form").dialog("close");
                        }, function (data) {
                            showMessage("add new acl module",data.msg,false);
                        })
                    },
                    "cancel": function () {
                        $("#dialog-aclModule-form").dialog("close");
                    }
                }
            });

        });
        function updateAclModule(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/aclModule/save.json" : "/sys/aclModule/update.json",
                data: $("#aclModuleForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        loadAclModuleTree();
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

        function recursiveRenderAclModuleSelect(aclModuleList,level) {
            level = level | 0;
            if(aclModuleList && aclModuleList.length>0) {
                $(aclModuleList).each(function (i,aclModule) {
                    aclModuleMap[aclModule.id] = aclModule;
                    var blank = "";
                    if(level > 1) {
                        for(var j = 3; j<=level;j++) {
                            blank += "..";
                        }
                        blank += "「 ";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>",{id: aclModule.id,name: blank+aclModule.name});
                    if(aclModule.aclModuleList && aclModule.aclModuleList.length > 0) {
                        recursiveRenderAclModuleSelect(aclModule.aclModuleList,level+1);
                    }
                })
            }
        }

        function recursiveRenderAclModule(aclModuleList) {
            if(aclModuleList && aclModuleList.length > 0) {
                $(aclModuleList).each(function (i,aclModule) {
                    aclModuleMap[aclModule.id] = aclModule;
                    if(aclModule.aclModuleList && aclModule.aclModuleList.length > 0) {
                        var rendered = Mustache.render(aclModuleListTemplate, {
                            aclModuleList: aclModule.aclModuleList,
                            "showDownAngle": function () {
                                return function (text,render) {
                                    return (this.aclModuleList && this.aclModuleList.length > 0) ? "" : "hidden";
                                }
                            },
                            "displayClass": function () {
                                return "hidden";
                            }
                        });
                        //setting html
                        $("#aclModule_" +aclModule.id).append(rendered);
                        recursiveRenderAclModule(aclModule.aclModuleList);
                    }

                })
            }
        }
        function bindAclModuleClick() {
            $(".sub-aclModule").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                $(this).parent().parent().parent().children().children(".aclModule-name").toggleClass("hidden");
                if($(this).is(".fa-angle-double-down")) {
                    $(this).removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
                } else{
                    $(this).removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
                }
            });
            $(".aclModule-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                handleAclModuleSelected(aclModuleId);
            });
            $(".aclModule-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                var aclModuleName = $(this).attr("data-name");
                if (confirm("Confirm to delete[" + aclModuleName + "]?")) {
                    $.ajax({
                        url: "/sys/aclModule/delete.json",
                        data: {
                            id: aclModuleId
                        },
                        success: function (result) {
                            if (result.ret) {
                                showMessage("Delete[" + aclModuleName + "]", "Successfully", true);
                                loadAclModuleTree();
                            } else {
                                showMessage("Delete[" + aclModuleName + "]", result.msg, false);
                            }
                        }
                    });
                }
            });

            $(".aclModule-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                $("#dialog-aclModule-form").dialog({
                    modal: true,
                    title: "edit new module",
                    open: function (event,ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = "<option value=\"0\">-</option>";
                        recursiveRenderAclModuleSelect(aclModuleList,1);
                        $("#aclModuleForm")[0].reset();
                        $("#parentId").html(optionStr);
                        $("#aclModuleId").val(aclModuleId);
                        var targetAclModule = aclModuleMap[aclModuleId];
                        if(targetAclModule) {
                            $("#parentId").val(targetAclModule.parentId);
                            $("#aclModuleName").val(targetAclModule.name);
                            $("#aclModuleRemark").val(targetAclModule.remark);
                            $("#aclModuleSeq").val(targetAclModule.seq);
                            $("#aclModuleStatus").val(targetAclModule.remark);
                        }
                    },
                    buttons : {
                        "update" : function(e) {
                            e.preventDefault();
                            updateAclModule(false, function (data) {
                                $("#dialog-aclModule-form").dialog("close");
                            }, function (data) {
                                showMessage("update acl module",data.msg,false);
                            })
                        },
                        "cancel": function () {
                            $("#dialog-aclModule-form").dialog("close");
                        }
                    }
                });

            })
        }

        function handleAclModuleSelected(aclModuleId) {
            if(lastClickAclModuleId != -1) {
                var lastAclModule = $("#aclModule_"+lastClickAclModuleId+" .dd2-content:first");
                lastAclModule.removeClass("btn-yellow");
                lastAclModule.removeClass("no-hover");
            }
            var currentAclModule = $("#aclModule_" + aclModuleId + " .dd2-content:first");
            currentAclModule.addClass("btn-yellow");
            currentAclModule.addClass("no-hover");
            lastClickAclModuleId= aclModuleId;
            loadAclList(aclModuleId);
        }
        function loadAclList(aclModuleId) {
            var pageSize = $("#pageSize").val();
            var url = "/sys/acl/page.json?aclModuleId=" + aclModuleId;
            var pageNo = $("#aclPage .pageNo").val() || 1;
            $.ajax({
                url : url,
                data: {
                    pageSize: pageSize,
                    pageNo: pageNo
                },
                success: function (result) {
                    renderAclListAndPage(result, url);
                }
            })
         //console.log("load acl list , id:" +aclModuleId)
        }
        function renderAclListAndPage(result,url) {
            if(result.ret) {
                if (result.data.total > 0){
                    var rendered = Mustache.render(aclListTemplate, {
                            aclList: result.data.data,
                            "showAclModuleName": function () {
                                return aclModuleMap[this.aclModuleId].name;
                            },
                            "showStatus": function () {
                                return this.status == 1 ? 'valid' : 'invalid';
                            },
                            "showType":function () {
                                return this.type == 1 ? 'menu' : (this.type == 2 ? 'button' : 'others');
                            },
                            "bold": function () {
                                return function (text,render) {
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
                        }
                       );
                    $("#aclList").html(rendered);
                    bindAclClick();
                    $.each(result.data.data, function(i, acl) {
                        aclMap[acl.id] = acl;
                    })

            } else {
                    $("#aclList").html("");
                }
                var pageSize = $("#pageSize").val();
                var pageNo = $("#userPage .pageNo").val() || 1;
                renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.data.length : 0, "aclPage", renderAclListAndPage);
            }
            else {
                showMessage("Obtain permission list", result.msg, false);
            }
        }
        function bindAclClick() {
            $(".acl-role").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclId = $(this).attr("data-id");
                $.ajax({
                    url: "/sys/acl/acls.json",
                    data: {
                        aclId: aclId
                    },
                    type: 'POST',
                    success: function (result) {
                        if(result.ret) {
                            console.log(result);
                            //todo: add dialog display
                        } else {
                            showMessage("Obtain permission allocated role and user",result.msg,false);
                        }
                    }
                })
            });
            $(".acl-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclId = $(this).attr("data-id");
                $("#dialog-acl-form").dialog({
                    modal: true,
                    title: "edit permission",
                    open: function (event,ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = "";
                        recursiveRenderAclModuleSelect(aclModuleList,1);
                        $("#aclForm")[0].reset();
                        $("#aclModuleSelectId").html(optionStr);
                        var targetAcl = aclMap[aclId];
                        if (targetAcl) {
                            $("#aclId").val(aclId);
                            $("#aclModuleSelectId").val(targetAcl.aclModuleId);
                            $("#aclStatus").val(targetAcl.status);
                            $("#aclType").val(targetAcl.type);
                            $("#aclName").val(targetAcl.name);
                            $("#aclUrl").val(targetAcl.url);
                            $("#aclSeq").val(targetAcl.seq);
                            $("#aclRemark").val(targetAcl.remark);
                        }
                    },
                    buttons : {
                        "update" : function(e) {
                            e.preventDefault();
                            updateAcl(false,function (data) {
                                $("#dialog-acl-form").dialog("close");
                            }, function (data) {
                                showMessage("edit permission",data.msg,false);
                            })
                        },
                        "cancel": function () {
                            $("#dialog-acl-form").dialog("close");
                        }
                    }
                });
            });
        }
        $(".acl-add").click(function () {
            $("#dialog-acl-form").dialog({
                modal: true,
                title: "add new permission",
                open: function (event,ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "";
                    recursiveRenderAclModuleSelect(aclModuleList,1);
                    $("#aclForm")[0].reset();
                    $("#aclModuleSelectId").html(optionStr);
                },
                buttons : {
                    "add" : function(e) {
                        e.preventDefault();
                        updateAcl(true,function (data) {
                            $("#dialog-acl-form").dialog("close");
                        }, function (data) {
                            showMessage("add new permission",data.msg,false);
                        })
                    },
                    "cancel": function () {
                        $("#dialog-acl-form").dialog("close");
                    }
                }
            });
        });
        function updateAcl(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/acl/save.json" : "/sys/acl/update.json",
                data: $("#aclForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret) {
                        loadAclList(lastClickAclModuleId);
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
