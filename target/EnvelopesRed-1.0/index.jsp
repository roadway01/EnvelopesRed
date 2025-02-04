<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>企业红包登录页面</title>
  <meta name="keywords" content="企业红包登录页面">
  <meta name="description" content="企业红包登录页面">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="Author" content="larry">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="format-detection" content="telephone=no">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/layui/css/layui.css" media="all">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/css/animate.css" media="all">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/css/login.css" media="all">
</head>
<body>
<div class="larry-main layui-layout animated shake larry-delay2" id="larry_login" style="margin-top: 94px;">
  <div class="title">企业红包</div>
  <p class="info">后台登录中心</p>
  <div class="user-info">
    <div class="avatar"><img src="<%=basePath%>public/images/admin.png" alt=""></div>
    <form class="layui-form" id="larry_form">
      <div class="layui-form-item">
        <label class="layui-form-label">用户名:</label>
        <input type="text" name="user_name" required="" lay-verify="required" aautocomplete="off" class="layui-input larry-input" placeholder="请输入您的用户名" autocomplete="off">
      </div>
      <div class="layui-form-item" id="password">
        <label class="layui-form-label">密码:</label>
        <input type="password" name="password" required="" lay-verify="required|password" aautocomplete="off" class="layui-input larry-input" placeholder="请输入您的密码" autocomplete="off">
      </div>
      <div class="layui-form-item larry-verfiy-code" id="larry_code">
        <input type="text" name="code" lay-verfy="" autocomplete="off" class="layui-input larry-input" placeholder="输入验证码">
        <div class="code">
          <div class="arrow"></div>
          <div class="code-img"><img src="<%=basePath%>public/images/captcha.jpg" alt="" id="codeimage"></div>
          <a id="change" title="看不清,点击更换验证码"><i></i></a>
        </div>
      </div>
      <div class="layui-form-item">
        <button class="layui-btn larry-btn" lay-filter="submit" lay-submit="">立即登录</button>
      </div>
    </form>
  </div>
  <div class="copy-right">© 2016-2017 Larry 版权所有  <a href="zzmedu.imwork.net" target="_blank">zzmedu.imwork.net</a></div>
</div>
</body>
</html>