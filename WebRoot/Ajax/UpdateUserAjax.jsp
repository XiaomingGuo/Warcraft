<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "";
	String id = (String)request.getParameter("Index");
	String sql = "select * from user_info where id='" + id +"'";
	if (hDBHandle.QueryDataBase(sql))
	{	
		if(hDBHandle.GetRecordCount() > 0)
		{
			hDBHandle.CloseDatabase();
			sql = "UPDATE user_info SET permission='" + (String)request.getParameter("Permission") + "' WHERE id='" + id +"'";
			hDBHandle.execUpate(sql);
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	else
	{
		hDBHandle.CloseDatabase();
		rtnRst = "产品类型为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
