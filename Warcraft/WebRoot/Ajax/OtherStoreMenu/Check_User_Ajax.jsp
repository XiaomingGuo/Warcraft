<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.PageParentClass" %>
<%
	String rtnRst = "remove$";
	String userName = (String)request.getParameter("userName").replace(" ", "");
	PageParentClass hPageHandle = new PageParentClass();
	
	if (userName != null&&userName.length() >= 2)
	{
		if(hPageHandle.CheckUserName(userName))
			rtnRst += "1$";
		else
			rtnRst += "error:请填写正确使用者信息!";
	}
	else
		rtnRst += "error:请填写正确使用者信息!";
	out.write(rtnRst);
%>
