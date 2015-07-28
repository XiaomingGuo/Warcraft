<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code");
	if(barcode.length() == 8)
	{
		rtnRst += hDBHandle.GetTypeByBarcode(barcode) + "$";
		rtnRst += hDBHandle.GetNameByBarcode(barcode) + "$";
		int iProRepertory = hDBHandle.GetRepertoryByBarCode(barcode, "product_storage");
		rtnRst += Integer.toString(iProRepertory) + "$";
		int iMatRepertory = hDBHandle.GetRepertoryByBarCode(barcode, "material_storage");
		rtnRst += Integer.toString(iMatRepertory) + "$";
	}
	else
	{
		rtnRst += "error$产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
