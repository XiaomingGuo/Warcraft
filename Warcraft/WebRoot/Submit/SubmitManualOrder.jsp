<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SubmitManualOrder" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>
<%
	SubmitManualOrder hPageSupport = new SubmitManualOrder();
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		request.setCharacterEncoding("UTF-8");
		String createDate = hPageSupport.GenYearMonthDayString();
		String OrderName = request.getParameter("OrderHeader").replace(" ", "") + request.getParameter("OrderName").replace(" ", "");
		
		if (OrderName.length() > 12)
		{
			hPageSupport.SubmitPoOrder(OrderName);
			List<List<String>> recordList = hPageSupport.getCustomerPORecord(OrderName);
			List<Integer> nextOrderQty = hPageSupport.getCustomerPOTotalQty(recordList);

			hPageSupport.CreateProduceOrderFromMaterialLack(OrderName, recordList, nextOrderQty);
		}
		response.sendRedirect("../Query_Order.jsp");
	}
%>
