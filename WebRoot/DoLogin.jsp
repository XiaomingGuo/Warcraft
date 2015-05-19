<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String KeyWord = "";
	boolean bLoginSuccessful = false;
%>

<%
	//session.invalidate();
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("name");
	String key = request.getParameter("key");
	if(name == null || key == null || name.isEmpty() || key.isEmpty())
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String sql = "select password from user_info where name=\"" + name +"\"" ;
		boolean a = hDBHandle.QueryDataBase(sql);
		KeyWord = hDBHandle.GetSingleString("password");
	
		mylogon.setUsername(name);
		mylogon.setUserpassword(KeyWord);
		String mess = mylogon.checkuser();
		
		if (KeyWord != null && KeyWord.equals(request.getParameter("key")))
		{
			bLoginSuccessful = true;
			session.setAttribute("logonuser", mylogon);
			response.sendRedirect("MainPage.jsp");
		}
		else
		{
			session.setAttribute("logonuser", "");
			session.setAttribute("error", mess);
%>
		<html>
			<head>    
				<title>系统登陆</title>
				<link rel='stylesheet' type='text/css' href='css/style.css'>
			</head>
  
			<body>
				<div align="center">
					<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					<form>
						<fieldset style='width: 500'>
			  				<legend><font  SIZE="+2" style="font-weight:bold;">登陆失败</font></legend><br><br>
			  				<table align="center" width="400">
			  					<tr>
			  						<td>
			  							您的用户名或密码有错误！！！
			  						</td>
			  					</tr>
			  					<tr>
			  						<td>
							  			<br><br><input type='button' onclick="history.go(-1);" value='返回' class="button">
			  						</td>
			  					</tr>
			  				</table>
						</fieldset>
					</form>
				</div>
			</body>
		</html>
<%
		}
	}
%>
