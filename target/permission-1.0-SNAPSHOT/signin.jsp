<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html lang="zh-CN">
<%
    String ret = request.getParameter("ret");
    if(StringUtils.isNotBlank(ret)) {
        ret = URLEncoder.encode(ret);
    } else {
        ret = "";
    }
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">

    <title>Sign in</title>
    <style>
     body {
         background-image: url("image/bg.jpg");
         background-repeat: no-repeat;
         background-size: cover;
     }
    </style>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
<body style="width: 100%;height: 100%">
<div style="top: 50%;left: 50%;width: 300px; height: 300px; margin: -150px 0 0 -150px;position: absolute" >
    <form class="form-signin" action="/login.page?ret=<%=ret%>" method="post">
        <h1 style="color: #fff;text-shadow:0 0 10px;letter-spacing: 1px;text-align: center;">Login</h1>
        <label for="inputEmail" class="sr-only">email/telephone</label>
        <input type="text" id="inputEmail" class="form-control" placeholder="Email/Telephone" name="username" required autofocus value="${username}">
        <label for="inputPassword" class="sr-only">password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required >
        <div class="checkbox" style="color: red;">${error}</div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
