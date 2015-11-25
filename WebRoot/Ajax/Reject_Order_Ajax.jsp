<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.Customer_Po" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.GetRecordByOrderName(order_name);
		List<String> delPoList = hPORHandle.getDBRecordList("po_name");

		if(delPoList != null && delPoList.size() > 0)
		{
			hPORHandle.GetRecordByPOName(delPoList.get(0));
			List<String> delOrderList = hPORHandle.getDBRecordList("Order_Name");
			hPORHandle.DeleteRecordByPOName(delPoList);
			Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
			hPOHandle.DeleteRecordByOrderNameName(delOrderList);
			Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
			hCPHandle.DeleteRecordByPOName(delPoList);
		}
	}
	out.write(rtnRst);
%>
