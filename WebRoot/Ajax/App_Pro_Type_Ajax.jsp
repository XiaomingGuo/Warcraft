<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Vendor_Info" %>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String storeroom=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	Vendor_Info hVIHandle = new Vendor_Info(new EarthquakeManagement());
	if (storeroom.indexOf("原材料库") < 0)
	{
		hVIHandle.GetRecordExceptStoreroom("原材料库");
	}
	else
	{
		hVIHandle.GetRecordByStoreroom(storeroom);
	}
	List<String> vendor_name = hVIHandle.getDBRecordList("vendor_name");
	if (vendor_name != null)
	{
		for(int i = 0; i < vendor_name.size(); i++)
		{
			rtnRst += vendor_name.get(i);
			rtnRst += '$';
		}
	}
	
	rtnRst += "#remove$";
	Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
	hPTHandle.GetRecordByStoreroom(storeroom);
	List<String> pro_type = hPTHandle.getDBRecordList("name");
	if (pro_type != null)
	{
		for(int i = 0; i < pro_type.size(); i++)
		{
			rtnRst += pro_type.get(i);
			rtnRst += "$";
		}
	}
	
	out.write(rtnRst);
%>
