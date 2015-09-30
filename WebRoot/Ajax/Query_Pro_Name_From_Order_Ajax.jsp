<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> pro_info = null;
	List<String> pro_type = null;
%>
<%
	String pro_order=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	//product_info Database query
	Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
	pro_info = hPORHandle.GetBarCodeByOrderName(pro_order);
	
	if (pro_info != null)
	{
		pro_type = new ArrayList<String>();
		for(int i = 0; i < pro_info.size(); i++)
		{
			String temp_type = "";
			String temp_Code = Integer.toString(Integer.parseInt(pro_info.get(i)) - 10000000);
			pro_type.add(hDBHandle.GetTypeByBarcode(temp_Code)+"-"+hDBHandle.GetNameByBarcode(temp_Code));
		}
		for(int i = 0; i < pro_info.size(); i++)
		{
			rtnRst += pro_type.get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
