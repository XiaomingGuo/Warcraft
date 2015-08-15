<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "";
	String name = (String)request.getParameter("name");
	String password = (String)request.getParameter("password");
	String department = (String)request.getParameter("department");
	String permission = (String)request.getParameter("Permission");
	if (name != null&&password != null&&department != null&&!name.isEmpty()&&!password.isEmpty()&&!department.isEmpty())
	{
		String sql = "INSERT INTO user_info (name, password, department, permission) VALUES ('" + name + "','" + password + "','" + department + "','" + permission + "')";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
