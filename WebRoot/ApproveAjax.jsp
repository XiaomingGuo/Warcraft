<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	int sql_out_count = 0, sql_in_count = 0, used_count = 0;
	String[] keyArray = {"IN_QTY", "OUT_QTY"};
%>
<%
	String rtnRst = "";
	String sql = "select * from product_info where name=\"" + (String)request.getParameter("Pro_Name") +"\"" ;
	if (hDBHandle.QueryDataBase(sql))
	{
		List<List<String>> tempList = hDBHandle.GetAllDBColumnsByList(keyArray);
		sql_in_count = Integer.parseInt(tempList.get(0).get(0));
		sql_out_count = Integer.parseInt(tempList.get(1).get(0));
	}
	
	used_count = Integer.parseInt((String)request.getParameter("OUT_QTY"));
	
	if (sql_in_count - (sql_out_count + used_count) >= 0)
	{
		sql= "UPDATE product_info SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE name='" + (String)request.getParameter("Pro_Name") + "'";
		boolean execStatus = hDBHandle.execUpate(sql);
		
		sql= "UPDATE material_record SET isApprove='1' WHERE id='" + (String)request.getParameter("material_id") + "'";
		execStatus = hDBHandle.execUpate(sql);
	}
	else
	{
		rtnRst = (String)request.getParameter("Pro_Name") + ": 数量不足!";
	}
	out.write(rtnRst);
%>
