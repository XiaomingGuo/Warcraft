<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> pro_type = null;
%>
<%
	String storeroom=(String)request.getParameter("FilterKey1");
	String rtnRst = "remove$";
	String sql = "select * from product_type where storeroom='" + storeroom +"'";
	if (hDBHandle.QueryDataBase(sql))
	{
		pro_type = hDBHandle.GetAllStringValue("name");
	}
	if (pro_type != null)
	{
		for(int i = 0; i < pro_type.size(); i++)
		{
			rtnRst += pro_type.get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
