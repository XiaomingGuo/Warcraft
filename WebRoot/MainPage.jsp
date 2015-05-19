<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	String message="";
	if(session.getAttribute("logonuser")==null){
		response.sendRedirect("tishi.jsp");
	}
	else{
		message="您好！"+mylogon.getUsername()+"</b> [女士/先生]！欢迎登录！";
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="./css/style.css">
  </head>
  
  <body>
  	<h2>您好！<jsp:getProperty property="username" name="mylogon"/></b>！欢迎登录！</h2>
  	<center>
  		<h1>
		    <nav>
		    	<ul>
		    		<li><a href="MainPage.jsp">首页</a></li>
		    		<li><a href="Application.jsp">申领</a></li>
		    		<li><a href="Query.jsp">查询</a></li>
		    		<li><a href="Query.jsp">录入</a></li>
		    		<li><a href="Query.jsp">报表</a></li>
		    		<li><a href="Summary.jsp">余量汇总</a></li>
		    		<li><a href="Quit.jsp">退出</a></li>
		    	</ul>
		    </nav>
	    </h1>
    </center>
  </body>
</html>
<%
	}
%>