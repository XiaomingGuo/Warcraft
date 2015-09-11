<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	String pro_type=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	proInfo = hDBHandle.GetProductInfo(pro_type);
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
