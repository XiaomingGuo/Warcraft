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
		String appProduct_QTY = request.getParameter("QTY");
		String Total_QTY = request.getParameter("Total_QTY");
		
		//product_type Database query
		//String sql = "INSERT INTO product_type (name) VALUES ('电脑附件')";
		String sql = "INSERT INTO material_record (proposer, material_name, QTY) VALUES (\"" + userName + "\", \"" + appProduct_name + "\", " + appProduct_QTY + ")";
		int totalCount = Integer.parseInt(Total_QTY);
		if ((Integer.parseInt(Total_QTY)-Integer.parseInt(appProduct_QTY)) >= 0)
		{
			if (appProduct_type.indexOf("请选择") < 0 && appProduct_name.indexOf("请选择") < 0 && hDBHandle.execUpate(sql))
			{
				response.sendRedirect("Query.jsp");
			}
			else
			{
				response.sendRedirect("Application.jsp");
			}
		}
		else
		{	
			response.sendRedirect("Application.jsp");
		}
	}
%>
