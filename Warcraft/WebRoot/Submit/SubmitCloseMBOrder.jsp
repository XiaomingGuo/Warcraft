<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String userName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appPONum = request.getParameter("po_select").replace(" ", "");
		//product_type Database query
		if (appPONum!=null && appPONum!="")
		{
			DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(appPONum));
			if(hCPHandle.getTableInstance().RecordDBCount() > 0)
			{
				hCPHandle.UpdateRecordByKeyList("status", "5", Arrays.asList("po_name"), Arrays.asList(appPONum));
			}
			else
			{
				session.setAttribute("error", "该po单没有出货记录!");
				response.sendRedirect("../tishi.jsp");
			}
		}
		response.sendRedirect("../Close_MB_Order.jsp");
	}
%>
