<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String sql=(String)request.getParameter("id");
	String rtnRst = "";
	proInfo = hDBHandle.GetProductInfo(sql);
	if (proInfo != null)
	{
		for(int i = 0; i < proInfo.size(); i++)
		{
			rtnRst += proInfo.get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
