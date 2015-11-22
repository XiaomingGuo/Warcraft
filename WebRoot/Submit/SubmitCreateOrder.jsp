<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SubmitCreateOrder" %>
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
		String appPOName = request.getParameter("po_select").replace(" ", "");
		SubmitCreateOrder hPageSupport = new SubmitCreateOrder();
		
		List<List<String>> recordList = null;
		List<String> customerQty = new ArrayList<String>();
		if (!appPOName.isEmpty())
		{
			recordList = hPageSupport.getCustomerPORecord(appPOName);
		}
		if (recordList != null)
		{
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				customerQty.add(request.getParameter(Integer.toString(iRow+1) + "_QTY").replace(" ", ""));
			}
		}
		hPageSupport.CreateCustomerOrder(appPOName, recordList, customerQty);
		
		response.sendRedirect("../Query_Order.jsp");
	}
%>
