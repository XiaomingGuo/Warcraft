<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "";
	String name = (String)request.getParameter("product_type");
	String password = (String)request.getParameter("product_name");
	if (name != null&&password != null)
	{
		//String sql = "INSERT INTO user_info (name, password, department, permission) VALUES ('" + name + "','" + password + "','" + department + "','" + permission + "')";
		//hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
