<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.User_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "";
	String id = (String)request.getParameter("Index").replace(" ", "");
	User_Info hUIHandle = new User_Info(new EarthquakeManagement());
	hUIHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(id));
	if(hUIHandle.RecordDBCount() > 0)
	{
		hUIHandle.UpdateRecordByKeyList("permission", (String)request.getParameter("Permission"), Arrays.asList("id"), Arrays.asList(id));
	}
	out.write(rtnRst);
%>
