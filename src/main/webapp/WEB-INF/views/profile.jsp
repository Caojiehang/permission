<%--
  Created by IntelliJ IDEA.
  User: jiehangcao
  Date: 2019-07-24
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
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
        <li class="active">User Profile</li>
    </ul><!-- /.breadcrumb -->

    <div class="nav-search" id="nav-search">
        <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off">
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
        </form>
    </div><!-- /.nav-search -->
</div>
<div class="page-content">
    <div class="page-header">
        <h1>
            User Profile Page
            <small>
                <i class="ace-icon fa fa-angle-double-right"></i>
                Display user profile
            </small>
        </h1>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="">
            <div id="user-profile" class="user-profile row">
                <div class="col-sm-offset-1 col-sm-10">
                    <div class="space"></div>
                    <form class="form-horizontal">
                        <div class="tabbable">
                            <ul class="nav nav-tabs padding-16">
                                <li class="active">
                                    <a data-toggle="tab" href="#edit-basic" aria-expanded="true">
                                        <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
                                        Basic Info
                                    </a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#edit-password" aria-expanded="false">
                                        <i class="blue ace-icon fa fa-key bigger-125"></i>
                                        Password
                                    </a>
                                </li>
                            </ul>
                            <div class="tab-content profile-edit-tab-content">
                                <div id="edit-basic" class="tab-pane active">
                                    <h4 class="header blue bolder smaller">General</h4>
                                    <div class="row">
                                        <div class="vspace-12-sm"></div>
                                        <div class="col-xs-12 col-sm-8">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label no-padding-right"
                                                       for="form-field-username">Username</label>
                                                <div class="col-sm-8">
                                                    <input class="col-xs-12 col-sm-10" type="text"
                                                           id="form-field-username" placeholder="Username"
                                                           value="alexdoe">
                                                </div>
                                            </div>
                                            <div class="space-4"></div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="space-4"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-comment">Comment</label>
                                        <div class="col-sm-9">
                                            <textarea id="form-field-comment"></textarea>
                                        </div>
                                    </div>
                                    <div class="space"></div>
                                    <h4 class="header blue bolder smaller">Contact</h4>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-email">Email</label>

                                        <div class="col-sm-9">
                                            <span class="input-icon input-icon-right">
                                                <input type="email" id="form-field-email" value="alexdoe@gmail.com">
                                                <i class="ace-icon fa fa-envelope"></i>
                                            </span>
                                        </div>
                                    </div>
                                    </div>
                                    <div class="space-4"></div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-phone">Phone</label>

                                        <div class="col-sm-9">
                                            <span class="input-icon input-icon-right">
                                                <input class="input-medium input-mask-phone" type="text" id="form-field-phone">
                                                <i class="ace-icon fa fa-phone fa-flip-horizontal"></i>
                                            </span>
                                        </div>
                                    </div>
                                <div id="edit-password" class="tab-pane">
                                    <div class="space-10"></div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-pass1">New
                                            Password</label>

                                        <div class="col-sm-9">
                                            <input type="password" id="form-field-pass1">
                                        </div>
                                    </div>

                                    <div class="space-4"></div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="form-field-pass2">Confirm
                                            Password</label>

                                        <div class="col-sm-9">
                                            <input type="password" id="form-field-pass2">
                                        </div>
                                    </div>
                                    <div class="clearfix form-actions">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button class="btn btn-info" type="button" id="submit-bt">
                                                <i class="ace-icon fa fa-check bigger-110"></i>
                                                Save
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div><!-- /.span -->
            </div><!-- /.user-profile -->
        </div>
        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div>
<script type="text/javascript">
$(function () {
    $.ajax({
        url: "/sys/users/userInfo.json",
        type: 'POST',
        success: function (result) {
            if(result.ret) {
                $("#form-field-username").val(result.data.username);
                $("#form-field-email").val(result.data.email);
                $("#form-field-phone").val(result.data.telephone);
                $("#form-field-comment").val(result.data.comment);
            }
        }
    });
    $("#submit-bt").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        updatePassword();
    });

    function updatePassword() {
        $.ajax({
            url:"/sys/users/changePassword.json",
            data: {
                telephone:$("#form-field-phone").val() ,
                password: $("#form-field-pass1").val(),
                confirmPassword: $("#form-field-pass2").val()
            },
            type: 'POST',
            success: function (result) {
                if(result.ret) {
                    $("#form-field-pass1").val("");
                    $("#form-field-pass2").val("");
                    console.log(result);
                    showMessage("Update Password","Successfully",false);
                } else {
                    console.log(result);
                    showMessage("Update Password",result.msg,false);
                }
            }
        })
    }

})
</script>
</body>
</html>
