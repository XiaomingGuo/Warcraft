<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.page.support.Product_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "remove$";
	String proName = request.getParameter("search_name").replace(" ", "");
	if(proName.length() > 0)
	{
		Product_Info hUIHandle = new Product_Info(new EarthquakeManagement());
		hUIHandle.GetBarCodeByName(proName);
		List<String> tempList = hUIHandle.getDBRecordList("Bar_Code");
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
