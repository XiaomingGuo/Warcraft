<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.SubmitCustomerPO" %>
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
		SubmitCustomerPO hPageSupport = new SubmitCustomerPO();

		if (!appPOName.isEmpty())
		{
			hPageSupport.SubmitPoOrder(appPOName);
			List<List<String>> recordList = hPageSupport.getCustomerPORecord(appPOName);
			List<Integer> nextOrderQty = hPageSupport.getCustomerPOTotalQty(recordList);

			nextOrderQty = hPageSupport.CreateProduceOrderFromProductStorage(appPOName, recordList, nextOrderQty);
			nextOrderQty = hPageSupport.CreateProduceOrderFromMaterialStorage(appPOName, recordList, nextOrderQty);
			hPageSupport.CreateProduceOrderFromMaterialLack(appPOName, recordList, nextOrderQty);
		}
		else
		{
			session.setAttribute("error", "Po 不能为空!");
			response.sendRedirect("tishi.jsp");
		}
	}
	response.sendRedirect("../Customer_PO.jsp");
%>
