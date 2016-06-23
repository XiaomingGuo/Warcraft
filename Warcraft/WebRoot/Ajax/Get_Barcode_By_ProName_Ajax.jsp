<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String proName = request.getParameter("search_name").replace(" ", "");
	if(proName.length() > 0)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.GetRecordByName(proName);
		List<String> tempList = hPIHandle.getDBRecordList("Bar_Code");
		for(int idx = 0; idx < tempList.size(); idx++)
		{
			rtnRst += tempList.get(idx) + (idx+1==tempList.size()?"$":",");
		}
	}
	else
	{
		rtnRst += "error:名称不存在?";
	}
	out.write(rtnRst);
%>
