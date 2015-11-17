<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String pro_type=(String)request.getParameter("FilterKey1").replace(" ", "");
	String rtnRst = "remove$";
	Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
	hPIHandle.GetRecordByProType(pro_type);
	List<String> proInfo = hPIHandle.getDBRecordList("name");
	if (proInfo != null)
	{
		for(int i = 0; i < proInfo.size(); i++)
		{
			rtnRst += proInfo.get(i);
			rtnRst += '$';
		}
	}
	out.write(rtnRst);
%>
