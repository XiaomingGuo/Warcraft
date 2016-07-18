<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_AllUserInfo_Ajax" %>
<%
	String rtnRst = "";
	String userName = request.getParameter("UserName").replace(" ", "");
	String department = request.getParameter("Department").replace(" ", "");
	if(userName.indexOf("请选择") >= 0||department.indexOf("请选择") >= 0)
	{
		Query_AllUserInfo_Ajax hPageHandle = new Query_AllUserInfo_Ajax();
		rtnRst += hPageHandle.GenerateReturnString(userName, department);
	}
	out.write(rtnRst);
%>
