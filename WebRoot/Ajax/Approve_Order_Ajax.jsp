<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name");
	
	if (order_name != null||order_name.indexOf("订单号") < 0)
	{
		String sql = "UPDATE product_order SET status='1' where Order_Name='" + order_name + "'";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
