<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.User_Info" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%
	String rtnRst = "";
	String name = (String)request.getParameter("name").replace(" ", "");
	String checkInId = (String)request.getParameter("check_in_id").replace(" ", "");
	String password = (String)request.getParameter("password").replace(" ", "");
	String department = (String)request.getParameter("department").replace(" ", "");
	String permission = (String)request.getParameter("Permission").replace(" ", "");
	if (name != null&&password != null&&department != null&&!name.isEmpty()&&!password.isEmpty()&&!department.isEmpty())
	{
		User_Info hUIHandle = new User_Info(new EarthquakeManagement());
		hUIHandle.AddARecord(checkInId, name, password, department, permission);
	}
	out.write(rtnRst);
%>
