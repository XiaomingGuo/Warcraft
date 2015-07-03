<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String po_name = request.getParameter("po_name");
	String barcode = request.getParameter("Bar_Code");
	if(barcode.length() == 8)
	{
		rtnRst += hDBHandle.GetNameByBarcode(barcode) + "$";
		int iRepertory = hDBHandle.GetRepertoryByBarCode(barcode, "product_storage");
		rtnRst += hDBHandle.GetPOQTY(barcode, po_name) + "$";
		rtnRst += Integer.toString(iRepertory) + "$";
	}
	else
	{
		rtnRst += "error$产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
