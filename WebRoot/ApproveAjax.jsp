<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String sql= "UPDATE material_record SET isApprove='1' WHERE id='" + (String)request.getParameter("material_id") + "'";
	boolean execStatus = hDBHandle.execUpate(sql);
	
	sql = "select OUT_QTY from product_info where name=\"" + (String)request.getParameter("Pro_Name") +"\"" ;
	
	hDBHandle.QueryDataBase(sql);
	int curr_count = Integer.parseInt(hDBHandle.GetSingleString("OUT_QTY"));
	int useed_count = Integer.parseInt((String)request.getParameter("OUT_QTY"));
	
	sql= "UPDATE product_info SET OUT_QTY='" + Integer.toString(curr_count+useed_count) + "' WHERE name='" + (String)request.getParameter("Pro_Name") + "'";
	execStatus = hDBHandle.execUpate(sql);
%>
