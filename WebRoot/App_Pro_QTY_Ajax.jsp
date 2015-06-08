<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	List<String> proInfo = null;
%>
<%
	int iRepertory = 0;
	String rtnRst = "remove$";
	List<String> barcodeList = null;
	String pro_name = (String)request.getParameter("product_name");
	String sql= "select Bar_Code from product_info where name='"+pro_name+"'";
	if (hDBHandle.QueryDataBase(sql))
	{
		barcodeList = hDBHandle.GetAllStringValue("Bar_Code");
	}
	for (int i = 0; i < barcodeList.size(); i++)
	{
		String bar_Code = barcodeList.get(i);
		iRepertory += hDBHandle.GetIN_QTYByBarCode(bar_Code) - hDBHandle.GetOUT_QTYByBarCode(bar_Code);
		rtnRst += bar_Code + "$";
	}
	rtnRst += Integer.toString(iRepertory);
	out.write(rtnRst);
%>
