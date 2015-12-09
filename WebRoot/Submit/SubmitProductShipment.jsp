<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.Customer_Po" %>
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
		String userName=mylogon.getUsername();
		request.setCharacterEncoding("UTF-8");
		String appPONum = request.getParameter("po_select").replace(" ", "");
		//product_type Database query
		if (appPONum!=null && appPONum!="")
		{
			Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
			hPORHandle.GetRecordByPOName(appPONum);
			if(hPORHandle.RecordDBCount() > 0)
			{
				List<String> order_List = hPORHandle.getDBRecordList("Order_Name");
				Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
				for (int iIndex = 0; iIndex < order_List.size(); iIndex++)
				{
					hPOHandle.UpdateRecordByKeyWord("status", "2", "Order_Name", order_List.get(iIndex));
				}
				Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
				hCPHandle.UpdateRecordByKeyWord("status", "2", "po_name", appPONum);
			}
			else
			{
				session.setAttribute("error", "该po单无生产单生成!");
				response.sendRedirect("../tishi.jsp");
			}
		}
		response.sendRedirect("../Product_Shipment.jsp");
	}
%>
