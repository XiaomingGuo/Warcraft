<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.Product_Order" %>
<%@ page import="com.DB.operation.Customer_Po" %>
<%@ page import="com.DB.operation.Mb_Material_Po" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String order_name = request.getParameter("order_name").replace(" ", "");
	
	if (order_name != null||order_name.indexOf("生产单号") < 0)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(order_name));
		List<String> delPoList = hPORHandle.getDBRecordList("po_name");
		String delPoKeyWord = "poName", delOrderKeyWord = "orderName";
		for(int idx = 0; idx < delPoList.size(); idx++)
		{
			if(delPoList.get(idx).toLowerCase().indexOf("internal_po") >= 0)
			{
				delPoList.set(idx, order_name);
				delPoKeyWord = "orderName";
			}
		}

		if(delPoList != null && delPoList.size() > 0)
		{
			hPORHandle.QueryRecordByFilterKeyList(Arrays.asList(delPoKeyWord), Arrays.asList(delPoList.get(0)));
			List<String> delOrderList = hPORHandle.getDBRecordList("Order_Name");
			hPORHandle.DeleteAllRecordListByKeyWord(delPoKeyWord, delPoList);
			Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
			hPOHandle.DeleteAllRecordListByKeyWord(delOrderKeyWord, delOrderList);
			Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
			hCPHandle.DeleteAllRecordListByKeyWord("poName", delPoList);
			Mb_Material_Po hMMPRHandle = new Mb_Material_Po(new EarthquakeManagement());
			hMMPRHandle.DeleteAllRecordListByKeyWord("po_name", delPoList);
		}
	}
	out.write(rtnRst);
%>
