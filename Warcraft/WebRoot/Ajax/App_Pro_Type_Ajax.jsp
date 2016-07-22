<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Vendor_Info" %>
<%@ page import="com.DB.operation.Product_Type" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String storeroom=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	Vendor_Info hVIHandle = new Vendor_Info(new EarthquakeManagement());
	if (storeroom.contains("五金"))
	{
		hVIHandle.GetRecordExceptStoreroom(Arrays.asList("原材料库", "成品库", "半成品库"));
	}
	else
	{
		hVIHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storeroom));
	}
	List<String> vendor_name = hVIHandle.getDBRecordList("vendor_name");
	if (vendor_name != null)
	{
		for(int i = 0; i < vendor_name.size(); i++)
		{
			rtnRst += vendor_name.get(i) + '$';
		}
	}
	
	rtnRst += "#remove$";
	Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
	hVIHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storeroom));
	List<String> pro_type = hPTHandle.getDBRecordList("name");
	if (pro_type != null)
	{
		for(int i = 0; i < pro_type.size(); i++)
		{
			rtnRst += pro_type.get(i) + "$";
		}
	}
	
	out.write(rtnRst);
%>
