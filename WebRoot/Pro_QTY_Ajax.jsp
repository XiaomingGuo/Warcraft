<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	String rtnRst = "";
	String sql= "select IN_QTY from product_info where name='"+(String)request.getParameter("product_name")+"'";
	hDBHandle.QueryDataBase(sql);
	int IN_Count = Integer.parseInt(hDBHandle.GetSingleString("IN_QTY"));
	sql= "select OUT_QTY from product_info where name='"+(String)request.getParameter("product_name")+"'";
	hDBHandle.QueryDataBase(sql);
	int OUT_Count = Integer.parseInt(hDBHandle.GetSingleString("OUT_QTY"));
	
	rtnRst = Integer.toString(IN_Count - OUT_Count);
	out.write(rtnRst);
%>
