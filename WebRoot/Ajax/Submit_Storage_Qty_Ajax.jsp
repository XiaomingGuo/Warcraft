<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Material_Storage" %>
<%@ page import="com.DB.operation.Other_Storage" %>
<%@ page import="com.DB.operation.Product_Storage" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.Warcraft.Interface.ITableInterface"%>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String recordId = request.getParameter("recordId");
	
	if (storage_name != null||storage_name == "")
	{
		ITableInterface hDBHandle = null;
		if(storage_name.toLowerCase().indexOf("material_storage") >= 0)
			hDBHandle = new Material_Storage(new EarthquakeManagement());
		else if(storage_name.toLowerCase().indexOf("other_storage") >= 0)
			hDBHandle = new Other_Storage(new EarthquakeManagement());
		else if(storage_name.toLowerCase().indexOf("product_storage") >= 0)
			hDBHandle = new Product_Storage(new EarthquakeManagement());
		hDBHandle.UpdateRecordByKeyWord("isEnsure", "1", "id", recordId);
	}
	out.write(rtnRst);
%>
