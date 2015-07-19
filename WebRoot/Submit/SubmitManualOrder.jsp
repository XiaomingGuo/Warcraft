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
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		String appOrderHeader = request.getParameter("OrderHeader");
		String appOrderName = request.getParameter("OrderName");
		String OrderName = appOrderHeader + appOrderName;
		String sql = null;
		
		if (!appOrderName.isEmpty())
		{
			sql = "select * from product_order where Order_Name='" + OrderName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
				hDBHandle.execUpate(sql);
			}
			else
			{
				hDBHandle.CloseDatabase();
			}
		}
		response.sendRedirect("../Query_Order.jsp");
	}
%>
