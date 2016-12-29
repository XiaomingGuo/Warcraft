<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.JSPPageUtility" %>
<%
	String rtnRst = "remove$";
	String tableName = request.getParameter("TableName").replace(" ", "");
	String keyWord = request.getParameter("KeyWord").replace(" ", "");
	String keyVal = request.getParameter("KeyVal").replace(" ", "");
	JSPPageUtility hPageHandle = new JSPPageUtility();
	
	if (tableName != null&&keyVal != null)
	{
		if(hPageHandle.IsKeyValueExist(tableName, keyWord, keyVal))
			rtnRst += "1$";
		else
			rtnRst += "0$";
	}
	out.write(rtnRst);
%>
