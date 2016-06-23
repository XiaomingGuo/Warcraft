<%@page import="com.DB.operation.Product_Order_Record"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String ordername = (String)request.getParameter("Order_Name").replace(" ", "");
	
	if (ordername != null)
	{
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPOHandle.UpdateRecordByKeyList("status", "3", Arrays.asList("Order_Name"), Arrays.asList(ordername));
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(ordername));
		if (hPORHandle.RecordDBCount() > 0)
		{
			List<String> idList = hPORHandle.getDBRecordList("id");
			for (int index = 0; index < idList.size(); index++)
			{
				hPORHandle.UpdateRecordByKeyList("status", "3", Arrays.asList("id"), Arrays.asList(idList.get(index)));
			}
		}
	}
	out.write(rtnRst);
%>
