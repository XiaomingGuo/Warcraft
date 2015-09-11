<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "";
	String name = (String)request.getParameter("name").replace(" ", "");
	String password = (String)request.getParameter("password").replace(" ", "");
	String department = (String)request.getParameter("department").replace(" ", "");
	String permission = (String)request.getParameter("Permission").replace(" ", "");
	if (name != null&&password != null&&department != null&&!name.isEmpty()&&!password.isEmpty()&&!department.isEmpty())
	{
		String sql = "INSERT INTO user_info (name, password, department, permission) VALUES ('" + name + "','" + password + "','" + department + "','" + permission + "')";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
