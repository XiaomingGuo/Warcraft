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
		request.setCharacterEncoding("UTF-8");
		String appProduct_type = request.getParameter("product_type");
		String appProduct_name = request.getParameter("product_name");
		String appProduct_QTY = request.getParameter("QTY");
		
		String sql = "select * from product_info where name='" + appProduct_name + "'";
		hDBHandle.QueryDataBase(sql);
		if (hDBHandle.GetRecordCount() > 0)
		{	
			int sqlCount = hDBHandle.GetSingleInt("IN_QTY");
			sql= "UPDATE product_info SET IN_QTY='" + Integer.toString(Integer.parseInt(appProduct_QTY)+sqlCount) + "' WHERE name='" + appProduct_name + "'";
			if (appProduct_type.indexOf("请选择") < 0 && appProduct_name.indexOf("请选择") < 0 && hDBHandle.execUpate(sql))
			{
				response.sendRedirect("AddMaterial.jsp");
			}
			else
			{
				response.sendRedirect("AddMaterial.jsp");
			}
		}
		else
		{
			//product_type Database query
			sql = "INSERT INTO product_info (name, product_type, IN_QTY) VALUES ('" + appProduct_name + "', '" + appProduct_type + "', '" + appProduct_QTY + "')";
			if (appProduct_type.indexOf("请选择") < 0 && appProduct_name.indexOf("请选择") < 0 && hDBHandle.execUpate(sql))
			{
				response.sendRedirect("AddMaterial.jsp");
			}
			else
			{
				response.sendRedirect("AddMaterial.jsp");
			}
		}
	}
%>
