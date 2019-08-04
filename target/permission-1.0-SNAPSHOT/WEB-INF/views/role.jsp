<%@ taglib prefix="acl" uri="checkButton" %>
<%--
  Created by IntelliJ IDEA.
  User: jiehangcao
  Date: 2019-07-17
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Role</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <link rel="stylesheet" href="/ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/assets/css/bootstrap-duallistbox.min.css" type="text/css">
    <script type="text/javascript" src="/ztree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript" src="/assets/js/jquery.bootstrap-duallistbox.min.js"></script>
    <style type="text/css">
        .bootstrap-duallistbox-container .moveall, .bootstrap-duallistbox-container .removeall {
            width: 50%;
        }

        .bootstrap-duallistbox-container .move, .bootstrap-duallistbox-container .remove {
            width: 49%;
        }
    </style>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        Role management
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            Maintain roles and users, roles and permissions
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            Role list&nbsp;&nbsp;
            <a class="green" href="#">
                <acl:checkPermission code="20190802195540_35">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 role-add"></i>
                </acl:checkPermission>
            </a>
        </div>
        <div id="roleList"></div>
    </div>
    <div class="col-sm-9">
        <div class="tabbable" id="roleTab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#roleAclTab">
                        Role and Permission
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#roleUserTab">
                        Role and User
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="roleAclTab" class="tab-pane fade in active">
                    <ul id="roleAclTree" class="ztree"></ul>
                    <button class="btn btn-info saveRoleAcl" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        save
                    </button>
                </div>

                <div id="roleUserTab" class="tab-pane fade">
                    <div class="row">
                        <div class="box1 col-md-6">List of users to select</div>
                        <div class="box1 col-md-6">List of selected users</div>
                    </div>
                    <select multiple="multiple" size="10" name="roleUserList" id="roleUserList">
                    </select>
                    <div class="hr hr-16 hr-dotted"></div>
                    <button class="btn btn-info saveRoleUser" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        save
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-role-form" style="display: none;">
    <form id="roleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td><label for="roleName">Name</label><label style="color: #be0000">*</label></td>
                <td>
                    <input type="text" name="name" id="roleName" value="" class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="roleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="roleStatus">Status</label><label style="color: #be0000">*</label></td>
                <td>
                    <select id="roleStatus" name="status" data-placeholder="status" style="width: 150px;">
                        <option value="1">available</option>
                        <option value="0">frozen</option>
                    </select>
                </td>
            </tr>
            <td><label for="roleRemark">Comment</label></td>
            <td><textarea name="remark" id="roleRemark" class="text ui-widget-content ui-corner-all" rows="3"
                          cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="roleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#roleList}}
        <li class="dd-item dd2-item role-name" id="role_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
            <acl:checkPermission code="20190802195505_28">
                <a class="green role-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
            </acl:checkPermission>
                &nbsp;
                <acl:checkPermission code="20190802194525_55">
                <a class="red role-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                 </acl:checkPermission>
                </a>
            </span>
            </div>
        </li>
    {{/roleList}}
</ol>

</script>

<script id="selectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}" selected="selected">{{username}}</option>
{{/userList}}

</script>

<script id="unSelectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}">{{username}}</option>
{{/userList}}

</script>

<script type="text/javascript">
    $(function () {
        var roleMap = {};
        var lastRoleId = -1;
        var selectFirstTab = true;
        var hasMultiSelect = false;

        var roleListTemplate = $("#roleListTemplate").html();
        Mustache.parse(roleListTemplate);

        var selectedUsersTemplate = $("#selectedUsersTemplate").html();
        Mustache.parse(selectedUsersTemplate);

        var unselectedUsersTemplate = $("#unSelectedUsersTemplate").html();
        Mustache.parse(unselectedUsersTemplate);

        loadRoleList();

        <!-- 树结构相关 开始 -->
        var zTreeObj = [];
        var modulePrefix = 'm_';
        var aclPrefix = 'a_';
        var nodeMap = {};

        var setting = {
            check: {
                enable: true,
                chkDisabledInherit: true,
                chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
                autoCheckTrigger: true
            },
            data: {
                simpleData: {
                    enable: true,
                    rootPId: 0
                }
            },
            callback: {
                onClick: onClickTreeNode
            }
        };

        function onClickTreeNode(e, treeId, treeNode) { // 绑定单击事件
            var zTree = $.fn.zTree.getZTreeObj("roleAclTree");
            zTree.expandNode(treeNode);
        }

        function loadRoleList() {
            $.ajax({
                url: "/sys/role/list.json",
                success: function (result) {
                    if (result.ret) {
                        var rendered = Mustache.render(roleListTemplate, {
                            roleList: result.data
                        });
                        $("#roleList").html(rendered);
                        bindRoleClick();
                        $.each(result.data, function (i, role) {
                            roleMap[role.id] = role;
                        })
                    } else {
                        showMessage("loading role list", result.msg, false);
                    }

                }
            });
        }
        function bindRoleClick() {
            $(".role-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                $("#dialog-role-form").dialog({
                    modal: true,
                    title: "edit role",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        $("#roleForm")[0].reset();
                        var targetRole = roleMap[roleId];
                        if (targetRole) {
                            $("#roleId").val(roleId);
                            $("#roleName").val(targetRole.name);
                            $("#roleStatus").val(targetRole.status);
                            $("#roleRemark").val(targetRole.remark);
                        }
                    },
                    buttons: {
                        "update": function (e) {
                            e.preventDefault();
                            updateRole(false, function (data) {
                                $("#dialog-role-form").dialog("close");
                            }, function (data) {
                                showMessage("edit role", data.msg, false);
                            })
                        },
                        "cancel": function () {
                            $("#dialog-role-form").dialog("close");
                        }
                    }
                })
            });
            $(".role-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                var roleName = $(this).attr("data-name");
                if (confirm("Confirm to delete[" + roleName + "]?")) {
                    $.ajax({
                        url: "/sys/role/delete.json",
                        data: {
                            roleId: roleId
                        },
                        success: function (result) {
                            if (result.ret) {
                                showMessage("Delete [" + roleName + "]", "Successfully", true);
                                loadRoleList();
                            } else {
                                showMessage("Delete[" + roleName + "]", result.msg, false);
                            }
                        }
                    });
                }
            });
            $(".role-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                handleRoleSelected(roleId);
            });
        }

        function handleRoleSelected(roleId) {
            if (lastRoleId != -1) {
                var lastRole = $("#role_" + lastRoleId + " .dd2-content:first");
                lastRole.removeClass("btn-yellow");
                lastRole.removeClass("no-hover");
            }
            var currentRole = $("#role_" + roleId + " .dd2-content:first");
            currentRole.addClass("btn-yellow");
            currentRole.addClass("no-hover");
            lastRoleId = roleId;
            $('#roleTab a:first').trigger('click');
            if (selectFirstTab) {
                loadRoleAcl(roleId);
            }
        }


        function loadRoleAcl(selectedRoleId) {
            //ztree
            if (selectedRoleId == -1) {
                return;
            }
            $.ajax({
                url: "/sys/role/roleTree.json",
                data: {
                    roleId: selectedRoleId
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        renderRoleTree(result.data);
                    } else {
                        showMessage("loading permission data", result.msg, false);
                    }
                }
            });
            //console.log(selectedRoleId);
        }

        function getTreeSelectedId() {
            var treeObject = $.fn.zTree.getZTreeObj("roleAclTree");
            var nodes = treeObject.getCheckedNodes(true);
            var v = "";
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id.startsWith(aclPrefix)) {
                    v += "," + nodes[i].dataId;
                }
            }
            return v.length > 0 ? v.substring(1) : v;
        }

        function renderRoleTree(aclModuleList) {
            zTreeObj = [];
            recursivePrepareTreeData(aclModuleList);
            for (var key in nodeMap) {
                zTreeObj.push(nodeMap[key]);
            }
            $.fn.zTree.init($("#roleAclTree"), setting, zTreeObj);
        }

        function recursivePrepareTreeData(aclModuleList) {
            //prepare nodeMap
            if (aclModuleList && aclModuleList.length > 0) {
                $(aclModuleList).each(function (i, aclModule) {
                    var hasChecked = false;
                    if (aclModule.aclList && aclModule.aclList.length > 0) {
                        $(aclModule.aclList).each(function (i, acl) {
                            zTreeObj.push({
                                id: aclPrefix + acl.id,
                                pId: modulePrefix + acl.aclModuleId,
                                name: acl.name + (acl.type == 1 ? '(MENU)' : ''),
                                chkDisabled: !acl.hasAcl,
                                checked: acl.checked,
                                dataId: acl.id
                            });
                            if (acl.checked) {
                                hasChecked = true;
                            }
                        });
                    }
                    if ((aclModule.aclModuleList && aclModule.aclModuleList.length > 0) ||
                        (aclModule.aclList && aclModule.aclList.length > 0)) {
                        nodeMap[modulePrefix + aclModule.id] = {
                            id: modulePrefix + aclModule.id,
                            pId: modulePrefix + aclModule.parentId,
                            name: aclModule.name,
                            open: hasChecked
                        };
                        var tempAclModule = nodeMap[modulePrefix + aclModule.id];
                        while (hasChecked && tempAclModule) {
                            if (tempAclModule) {
                                nodeMap[tempAclModule.id] = {
                                    id: tempAclModule.id,
                                    pId: tempAclModule.pId,
                                    name: tempAclModule.name,
                                    open: true
                                }
                            }
                            tempAclModule = nodeMap[tempAclModule.pId];
                        }
                    }
                    recursivePrepareTreeData(aclModule.aclModuleList);
                });
            }
        }
        $(".role-add").click(function () {
            $("#dialog-role-form").dialog({
                modal: true,
                title: "add new role",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    $("#roleForm")[0].reset();
                },
                buttons: {
                    "add": function (e) {
                        e.preventDefault();
                        updateRole(true, function (data) {
                            $("#dialog-role-form").dialog("close");
                        }, function (data) {
                            showMessage("add new role", data.msg, false);
                        })
                    },
                    "cancel": function () {
                        $("#dialog-role-form").dialog("close");
                    }
                }
            })
        });
        $(".saveRoleAcl").click(function (e) {
            e.preventDefault();
            if (lastRoleId == -1) {
                showMessage("save role and permission", "please select role firstly", false);
                return;
            }
            $.ajax({
                url: "/sys/role/changeAcls.json",
                data: {
                    roleId: lastRoleId,
                    aclIds: getTreeSelectedId()
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        showMessage("save role and permission", "successfully", false);
                    } else {
                        showMessage("save role and permission", result.msg, false);
                    }
                }
            });

        });
        function updateRole(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/role/save.json" : "/sys/role/update.json",
                data: $("#roleForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        loadRoleList();
                        if (successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        if (failCallBack) {
                            failCallBack(result);
                        }
                    }
                }
            })
        }
        $("#roleTab a[data-toggle='tab']").on("shown.bs.tab", function (e) {
            if (lastRoleId == -1) {
                showMessage("load user and role", "please select role firstly", false);
                return;
            }
            if (e.target.getAttribute("href") == "#roleAclTab") {
                selectFirstTab = true;
                loadRoleAcl(lastRoleId);
                console.log(e.target.getAttribute("href"));
            } else {
                selectFirstTab = false;
                loadRoleUser(lastRoleId);
            }
        });
        function loadRoleUser(selectRoleId) {
            $.ajax({
                url: "/sys/role/users.json",
                data: {
                    roleId: selectRoleId
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        var renderedSelect = Mustache.render(selectedUsersTemplate, {
                            userList: result.data.selected
                        });
                        var renderedUnSelected = Mustache.render(unselectedUsersTemplate, {
                            userList: result.data.unselected
                        });
                        $("#roleUserList").html(renderedSelect + renderedUnSelected);

                        if (!hasMultiSelect) {
                            $('select[name="roleUserList"]').bootstrapDualListbox({
                                showFilterInputs: false,
                                moveOnSelect: false,
                                infoText: false
                            });
                            hasMultiSelect = true;
                        } else {
                            $('select[name="roleUserList"]').bootstrapDualListbox('refresh', true);
                        }
                    } else {
                        showMessage("loading role and user", result.msg, false);
                    }
                }
            });
        }
        $(".saveRoleUser").click(function (e) {
            e.preventDefault();
            if (lastRoleId == -1) {
                showMessage("save role and user", "please select role firstly", false);
                return;
            }
            $.ajax({
                url: "/sys/role/changeUsers.json",
                data: {
                    roleId: lastRoleId,
                    userIds: $("#roleUserList").val() ? $("#roleUserList").val().join(",") : ""
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        showMessage("save role and user", "successfully", false);
                    } else {
                        showMessage("save role and user", result.msg, false);
                    }
                }
            });

        });
    })
</script>
</body>
</html>
