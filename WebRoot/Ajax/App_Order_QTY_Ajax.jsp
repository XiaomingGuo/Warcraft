<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	int iProRepertory = 0, iMatRepertory = 0;
	String rtnRst = "remove$";
	String strBarcode = null;
	String pro_name = (String)request.getParameter("product_name").replace(" ", "");
	String pro_type = (String)request.getParameter("product_type").replace(" ", "");
	String sql= "select Bar_Code from product_info where name='"+pro_name+"' and product_type='"+ pro_type + "'";
	if (hDBHandle.QueryDataBase(sql))
	{
		strBarcode = hDBHandle.GetSingleString("Bar_Code");
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	iProRepertory = hDBHandle.GetRepertoryByBarCode(strBarcode, "product_storage");
	iMatRepertory = hDBHandle.GetRepertoryByBarCode(strBarcode, "material_storage");
	rtnRst += strBarcode + "$";
	rtnRst += Integer.toString(iProRepertory) + "$";
	rtnRst += Integer.toString(iMatRepertory) + "$";
	out.write(rtnRst);
%>
