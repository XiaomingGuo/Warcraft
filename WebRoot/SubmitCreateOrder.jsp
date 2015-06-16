<%@page import="com.mysql.fabric.xmlrpc.base.Data"%>
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
		String appOrderHead = request.getParameter("OrderHeader");
		String appOrderName = request.getParameter("OrderName");
		String orderName = appOrderHead + appOrderName;
		if (!appOrderHead.isEmpty() && !appOrderName.isEmpty())
		{
			String sql = "select * from product_order where Order_Name='" + orderName + "'";
			if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				sql = "INSERT INTO product_order (name, status) VALUES ('" + orderName + "', '0')";
				//hDBHandle.execUpate(sql);
			}
		}
		
		/*if (!appBarcode.isEmpty() && !appProductQTY.isEmpty() && !appPriceUnit.isEmpty())
		{
			String appTotalPrice = String.format("%.2f", Float.parseFloat(appPriceUnit)*Float.parseFloat(appProductQTY));
			Calendar mData = Calendar.getInstance();
			String batch_lot_Head = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
			int loopNum = 1;
			do
			{
				String batch_lot = batch_lot_Head + "-" + String.format("%02d", loopNum);
				String sql = "select * from material_storage where Bar_Code='" + appBarcode + "' and Batch_Lot='" + batch_lot + "'";
				hDBHandle.QueryDataBase(sql);
				if (hDBHandle.GetRecordCount() <= 0)
				{
					hDBHandle.CloseDatabase();
					//product_type Database query
					sql = "INSERT INTO material_storage (Bar_Code, Batch_Lot, IN_QTY, Price_Per_Unit, Total_Price) VALUES ('" + appBarcode + "', '" + batch_lot + "', '" + appProductQTY+ "', '" + appPriceUnit+ "', '" + appTotalPrice + "')";
					hDBHandle.execUpate(sql);
					break;
				}
				loopNum ++;
			}
			while(true);
			
		}*/
		response.sendRedirect("Query_Order.jsp");
	}
%>
