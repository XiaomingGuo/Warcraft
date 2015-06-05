<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> product_type = null, product_info = null;
%>
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
		
		//product_type Database query
		String sql = "select * from user_info where name='" + mylogon.getUsername() + "'";
		if (hDBHandle.QueryDataBase(sql))
		{
			product_type = hDBHandle.GetAllStringValue("name");
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>修改密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
	<script language="javascript" src="JS/jquery-1.11.3.min.js"></script>
  <body>
    <jsp:include page="MainMenu.jsp"/>
  	<center>
  		<br><br><br>
	   	<form name="ChangePassword" action = "SubmitPassword.jsp" method = "post">
	  	<table border="1">
		  	<tr>
		  		<th align="center">修改密码</th>
		  	</tr>
		  	<tr>
		  		<td align="right">
			  		<label>当前密码:</label>
				  	<input type="password" name="cur_password" id="cur_password">
			  	</td>
		  	</tr>
			<tr>
		  		<td align="right">
			  		<label>新密码:</label>
				  	<input type="password" name="new_password" id="new_password">
			  	</td>
			</tr>
			<tr>
		 		<td align="right">
			  		<label>确认新密码:</label>
				  	<input type="password" name="confirm_password" id="confirm_password">
			  	</td>
			</tr>
			<tr>
		 		<td align="right">
				  	<center><input value="确认" type="submit"></center>
			  	</td>
			</tr>
		</table>
	  	</form>
  	</center>
  </body>
</html>
<%
	}
%>