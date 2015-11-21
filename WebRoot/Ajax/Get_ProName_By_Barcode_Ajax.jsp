<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code").replace(" ", "");
	if(barcode.length() == 8)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.GetRecordByBarcode(hPIHandle.GetUsedBarcode(barcode, "product_storage"));
		String proType = hPIHandle.getDBRecordList("product_type").get(0);
		String proName = hPIHandle.getDBRecordList("name").get(0);

		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		hPTHandle.GetRecordByName(proType);
		String storeroom = hPIHandle.getDBRecordList("storeroom").get(0);
		
		if (!proType.isEmpty() && !proName.isEmpty())
		{
			rtnRst += storeroom + "$";
			rtnRst += proType + "$";
			rtnRst += proName + "$";
		}
		else
		{
			rtnRst += "error:这个八码不存在?";
		}
	}
	else
	{
		rtnRst += "error:八码有八位你不知道吗?";
	}
	out.write(rtnRst);
%>
