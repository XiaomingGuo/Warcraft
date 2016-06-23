<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String message="";
	if(session.getAttribute("logonuser")==null)
	{
		session.invalidate();
	}
	response.sendRedirect("Login.jsp");
%>

