<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<style>
	body{background:#ECF5FF}
	</style>
  </head>
  
  <body>
		<img src="IMAGE/Logo.jpg"><h2>常州市茂邦机械有限公司<h2>
		<h3>您好！<jsp:getProperty property="username" name="mylogon"/>！欢迎登录！</h3>
<%
		int temp = mylogon.getUserRight()&32;
		if(temp == 0)
	  	{
%>
		
		<a href = 'ChangePassword.jsp'>修改密码</a>
<%
	  	}
	  	else
	  	{
%>
		<a href = 'ChangePassword.jsp'>修改密码</a>
		<a href = 'UserManagement.jsp?BeginPage=1'>用户管理</a>

<%
  		}
%>
  	<center>
  		<h1>
	    	<ul>
	    		<li><a href="MainPage.jsp">首页</a></li>
	    		<li><a href="Application.jsp">申请</a></li>
	    		<li><a href="Query.jsp">查询</a></li>
	    		<li><a href="Approve.jsp">批准</a></li>
	    		<li><a href="Approve.jsp">订单录入</a></li>
	    		<li><a href="Approve.jsp">订单录入查询</a></li>
	    		<li><a href="AddMaterial.jsp">物料录入</a></li>
	    		<li><a href="QueryMaterial.jsp">物料录入查询</a></li>
	    		<li><a href="PersonReport.jsp">个人报表</a></li>
	    		<li><a href="MonthReport.jsp">月报表</a></li>
	    		<li><a href="Summary.jsp">库存</a></li>
	    		<li><a href="Quit.jsp">退出</a></li>
	    	</ul>
	    </h1>
    </center>
  </body>
</html>
<%
	}
%>