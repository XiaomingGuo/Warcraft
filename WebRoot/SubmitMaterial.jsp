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
		String appProduct_type = request.getParameter("product_type");
		String appProductname = request.getParameter("productname");
		String appBarcode = request.getParameter("barcode");
		String appPriceUnit = request.getParameter("PriceUnit");
		String appProductQTY = request.getParameter("QTY");
		if (!appProduct_type.isEmpty() && !appProductname.isEmpty() && !appBarcode.isEmpty())
		{
			String sql = "select * from product_info where Bar_Code='" + appBarcode + "'";
			hDBHandle.QueryDataBase(sql);
			if (hDBHandle.GetRecordCount() <= 0)
			{
				hDBHandle.CloseDatabase();
				//product_type Database query
				sql = "INSERT INTO product_info (name, Bar_Code, product_type) VALUES ('" + appProductname + "', '" + appBarcode + "', '" + appProduct_type + "')";
				hDBHandle.execUpate(sql);
			}
		}
		
		if (!appBarcode.isEmpty() && !appProductQTY.isEmpty() && !appPriceUnit.isEmpty())
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
		}
		response.sendRedirect("AddMaterial.jsp");
	}
%>
