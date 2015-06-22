<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	int sql_out_count = 0, sql_in_count = 0, used_count = 0;
%>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom");
	String sql = "select * from storeroom_name where name='" + storeroom +"'" ;
	if (!storeroom.isEmpty()&&hDBHandle.QueryDataBase(sql))
	{	
		if(hDBHandle.GetRecordCount() > 0)
		{
			hDBHandle.CloseDatabase();
			rtnRst = "库名已经存在!";
		}
		else
		{
			hDBHandle.CloseDatabase();
			sql = "INSERT INTO storeroom_name (name) VALUES ('" + storeroom + "')";
			hDBHandle.execUpate(sql);
		}
	}
	else
	{
		rtnRst = "库名为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
