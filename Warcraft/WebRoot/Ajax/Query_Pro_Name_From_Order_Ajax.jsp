<%@page import="com.DB.operation.Product_Info"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String pro_order=(String)request.getParameter("product_order").replace(" ", "");
	String rtnRst = "remove$";
	//product_info Database query
	Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
	hPORHandle.GetRecordByOrderName(pro_order);
	List<String> pro_info = hPORHandle.getDBRecordList("Bar_Code");
	if (pro_info != null)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		List<String> pro_type = new ArrayList<String>();
		for(int i = 0; i < pro_info.size(); i++)
		{
			String temp_type = "";
			String temp_Code = Integer.toString(Integer.parseInt(pro_info.get(i)) - 10000000);
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(temp_Code));
			pro_type.add(hPIHandle.getDBRecordList("product_type").get(0)+"$"+hPIHandle.getDBRecordList("name").get(0));
		}
		for(int i = 0; i < pro_info.size(); i++)
		{
			rtnRst += pro_type.get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
