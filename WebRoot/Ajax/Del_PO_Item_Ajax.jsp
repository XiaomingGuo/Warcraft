<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Customer_Po_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id").replace(" ", "");
	
	if (pro_id != null)
	{
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.DeleteRecordByKeyWord("id", Arrays.asList(pro_id));
	}
	out.write(rtnRst);
%>
