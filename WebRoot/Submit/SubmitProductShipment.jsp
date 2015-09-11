<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
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
		String appPONum = request.getParameter("po_select").replace(" ", "");
		//product_type Database query
		if (appPONum!=null && appPONum!="")
		{
			String sql = "select * from product_order_record where po_name='" + appPONum +"'";
			if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
			{
				List<String> order_List = hDBHandle.GetAllStringValue("Order_Name");
				for (int iIndex = 0; iIndex < order_List.size(); iIndex++)
				{
					sql= "UPDATE product_order SET status=2 WHERE Order_Name='" + order_List.get(iIndex) + "'";
					hDBHandle.execUpate(sql);
				}
				sql= "UPDATE customer_po SET status=2 WHERE po_name='" + appPONum + "'";
				hDBHandle.execUpate(sql);
			}
			else
			{
				hDBHandle.CloseDatabase();
				session.setAttribute("error", "该po单无生产单生成!");
				response.sendRedirect("../tishi.jsp");
			}
		}
		response.sendRedirect("../Product_Shipment.jsp");
	}
%>
