<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<jsp:useBean id="mylogon" class="com.safe.UserLogon.DoyouLogon" scope="session"/>

<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String userName="", appProduct_info = "";
%>
<%
	if(session.getAttribute("logonuser")==null){
		response.sendRedirect("tishi.jsp");
	}
	else{
		userName=mylogon.getUsername();
		appProduct_info = request.getParameter("product_type");
		appProduct_info = request.getParameter("product_info");
		
		//product_type Database query
		String sql = "insert into material_record (proposer, material_name, QTY) values (" + userName + ", " + appProduct_info + ", 1);";
		if (hDBHandle.QueryDataBase(sql))
		{
			response.sendRedirect("Application.jsp");
		}
		else{
		;
		}
	}
%>
