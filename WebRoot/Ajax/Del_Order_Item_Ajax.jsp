<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id");
	
	if (pro_id != null)
	{
		String sql = "delete from product_order_record where id='" + pro_id + "'";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
