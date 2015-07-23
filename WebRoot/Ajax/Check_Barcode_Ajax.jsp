<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String barcode = (String)request.getParameter("Bar_Code");
	
	if (barcode != null)
	{
		String sql = "select * from product_info where Bar_Code='" + hDBHandle.GetUsedBarcode(barcode, "product_info") + "'";
		if (hDBHandle.QueryDataBase(sql)&&hDBHandle.GetRecordCount() > 0)
		{
			rtnRst += "1$";
		}
		else
		{
			rtnRst += "0$";
		}
		hDBHandle.CloseDatabase();
	}
	out.write(rtnRst);
%>
