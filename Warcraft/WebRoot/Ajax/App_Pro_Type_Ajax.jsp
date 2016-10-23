<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.factory.DatabaseStore" %>
<%@ page import="com.Warcraft.SupportUnit.DBTableParent"%>
<%
	String storeroom=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	DBTableParent hVIHandle = new DatabaseStore("Vendor_Info");
	if (storeroom.contains("五金"))
	{
		hVIHandle.QueryRecordByExceptFilterList(Arrays.asList("storeroom", "storeroom", "storeroom"), Arrays.asList("原材料库", "成品库", "半成品库"));
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
	DBTableParent hPTHandle = new DatabaseStore("Product_Type");
	hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storeroom));
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
