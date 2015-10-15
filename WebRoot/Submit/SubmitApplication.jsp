<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.page.support.Other_Record" %>
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
		String proposerName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appProduct_type = request.getParameter("product_type").replace(" ", "");
		String appProduct_name = request.getParameter("product_name").replace(" ", "");
		String appBarcode = request.getParameter("bar_code").replace(" ", "");
		String appUserName = request.getParameter("user_name").replace(" ", "");
		String appProduct_QTY = request.getParameter("QTY").replace(" ", "");
		String Total_QTY = request.getParameter("Total_QTY").replace(" ", "");
		
		//product_type Database query
		if (appProduct_type.indexOf("请选择") < 0 && appProduct_name.indexOf("请选择") < 0 && !appProduct_QTY.isEmpty() && !Total_QTY.isEmpty())
		{
			Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
			if ((Integer.parseInt(Total_QTY)-Integer.parseInt(appProduct_QTY)) >= 0)
			{
				hORHandle.AddARecord(hORHandle.GetUsedBarcode(appBarcode, "other_record"), proposerName, appProduct_QTY, appUserName);
				response.sendRedirect("../Query.jsp");
			}
			else
			{	
				session.setAttribute("error", "申请这么多,你倒是看看家底儿啊!");
				response.sendRedirect("../tishi.jsp");
			}
		}
		else
		{
			session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
			response.sendRedirect("../tishi.jsp");
		}
}
%>
