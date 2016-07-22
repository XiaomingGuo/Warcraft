<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Storeroom_Name" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "";
	String storeroom = (String)request.getParameter("storeroom").replace(" ", "");

	if (!storeroom.isEmpty())
	{	
		Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
		hSNHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(storeroom));
		if(hSNHandle.RecordDBCount() > 0)
		{
			rtnRst = "库名已经存在!";
		}
		else
		{
			hSNHandle.AddARecord(storeroom);
		}
	}
	else
	{
		rtnRst = "库名为空或查询数据库出错!";
	}
	
	out.write(rtnRst);
%>
