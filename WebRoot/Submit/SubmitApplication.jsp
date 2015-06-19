<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	if(session.getAttribute("logonuser")==null)
	{
		response.sendRedirect("tishi.jsp");
	}
	else
	{
		String userName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appProduct_type = request.getParameter("product_type");
		String appProduct_name = request.getParameter("product_name");
		String appBarcode = request.getParameter("bar_code");
		String appProduct_QTY = request.getParameter("QTY");
		String Total_QTY = request.getParameter("Total_QTY");
		
		//product_type Database query
		if (appProduct_type.indexOf("请选择") > 0 || appProduct_name.indexOf("请选择") > 0 || appProduct_QTY == "" || Integer.parseInt(appProduct_QTY) <= 0 || Integer.parseInt(Total_QTY) <= 0)
		{
			String sql = "INSERT INTO other_record (proposer, Bar_Code, QTY) VALUES ('" + userName + "', '" + appBarcode + "', " + appProduct_QTY + ")";
			if ((Integer.parseInt(Total_QTY)-Integer.parseInt(appProduct_QTY)) >= 0)
			{
				hDBHandle.execUpate(sql);
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
