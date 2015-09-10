<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> pro_type = null, vendor_name = null;
%>
<%
	String storeroom=(String)request.getParameter("FilterKey1");
	String rtnRst = "remove$";
	String sql = "select * from vendor_info where storeroom='" + storeroom +"'";
	if (storeroom.indexOf("原材料库") < 0)
	{
		sql = "select * from vendor_info where storeroom!='原材料库'";
	}
	if (hDBHandle.QueryDataBase(sql))
	{
		vendor_name = hDBHandle.GetAllStringValue("vendor_name");
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	if (vendor_name != null)
	{
		for(int i = 0; i < vendor_name.size(); i++)
		{
			rtnRst += vendor_name.get(i);
			rtnRst += '$';
		}
	}
	
	rtnRst += "#remove$";
	sql = "select * from product_type where storeroom='" + storeroom +"'";
	if (hDBHandle.QueryDataBase(sql))
	{
		pro_type = hDBHandle.GetAllStringValue("name");
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	if (pro_type != null)
	{
		for(int i = 0; i < pro_type.size(); i++)
		{
			rtnRst += pro_type.get(i);
			rtnRst += "$";
		}
	}
	
	out.write(rtnRst);
%>
