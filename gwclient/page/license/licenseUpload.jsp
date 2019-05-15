<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.jspsmart.upload.*"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">
        <link rel="stylesheet" type="text/css" href="css/common.css">
        <link rel="stylesheet" type="text/css" href="css/css.css">
        <!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
        <script type="text/javascript">
    function toLoginPage() {
        location.href="login_login.do";
    }
    function exitSys() {
        if(confirm("确认退出系统？")){
            window.opener = null;
            window.open('','_self');
            window.close();
        }
    }     
    </script>
    </head>

    <body>
        <div style="text-align: center; margin-top: 150px;">
            <div class="bgDiv">
                <div style="height: 200px; width: 500px; margin-top: 150px; background-color: #f1f1f1;">
                    <div style="margin-top: 50px;">
                        <span style="font-size: 20px; padding-left: 13px;">授权文件上传成功,请重新登录系统!</span>
                    </div>
                    <div style="margin-top: 50px;">
                        <input type="button" name="login" class="btn1" value="登录系统" onclick="toLoginPage();">
                        <input type="button" name="login" class="btn1" value="退出" onclick="exitSys();">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
