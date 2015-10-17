<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.page.support.Customer_Po" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String appPOName = request.getParameter("POName").replace(" ", "");
		if (!appPOName.isEmpty())
		{
			Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
			hCPHandle.GetRecordByPoName(appPOName);
			if (hCPHandle.getDBRecordList("id").size() <= 0)
			{
				hCPHandle.AddARecord(appPOName);
			}
		}
	}
	response.sendRedirect("../Customer_PO.jsp");
%>
