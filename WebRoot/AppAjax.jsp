<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	String sql=(String)request.getParameter("FilterKey1");
	String rtnRst = "remove$";
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
