<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	boolean bLoginSuccessful = false;
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("name").replace(" ", "");
	String key = request.getParameter("key").replace(" ", "");
	if(name == null || key == null || name.isEmpty() || key.isEmpty())
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String mess = "";
		DatabaseStore hDBHandle = new DatabaseStore("User_Info");
		hDBHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(name));
		List<String> tempList = hDBHandle.getTableInstance().getDBRecordList("password");
		
		if(tempList.size() > 0)
		{
			String KeyWord = tempList.get(0);
			tempList = hDBHandle.getTableInstance().getDBRecordList("permission");
			int userRight = Integer.parseInt( tempList.get(0));
			
			mylogon.setUsername(name);
			mylogon.setUserpassword(KeyWord);
			mylogon.setUserRight(userRight);
			mess = mylogon.checkuser();
			
			if (KeyWord != null && KeyWord.equals(request.getParameter("key").replace(" ", "")))
			{
				bLoginSuccessful = true;
				session.setAttribute("logonuser", mylogon);
				response.sendRedirect("MainPage.jsp");
			}
		}
		
		if(!bLoginSuccessful)
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
				</div>
			</body>
		</html>
<%
		}
	}
%>
