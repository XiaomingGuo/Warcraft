<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<List<String>> recordList = null;
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String appOrderHead = request.getParameter("OrderHeader");
		String appOrderName = request.getParameter("OrderName");
		String orderName = appOrderHead + appOrderName;
		if (!appOrderHead.isEmpty() && !appOrderName.isEmpty())
		{
			String sql = "select * from product_order where Order_Name='" + orderName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO product_order (Order_Name, status) VALUES ('" + orderName + "', '0')";
				hDBHandle.execUpate(sql);
			}
		}
		response.sendRedirect("Query_Order.jsp");
	}
%>
