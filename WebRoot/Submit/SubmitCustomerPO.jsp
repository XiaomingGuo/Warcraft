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
		String appPOName = request.getParameter("POName");
		if (!appPOName.isEmpty())
		{
			String sql = "select * from customer_po where po_name='" + appPOName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO customer_po (po_name, status) VALUES ('" + appPOName + "', '0')";
				hDBHandle.execUpate(sql);
			}
		}
		response.sendRedirect("../Customer_PO.jsp");
	}
%>
