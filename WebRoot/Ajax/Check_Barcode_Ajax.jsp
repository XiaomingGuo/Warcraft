<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String barcode = (String)request.getParameter("Bar_Code").replace(" ", "");
	
	if (barcode != null)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.GetRecordByBarcode(barcode);
		if (hPIHandle.RecordDBCount() > 0)
			rtnRst += "1$";
		else
			rtnRst += "0$";
	}
	out.write(rtnRst);
%>
