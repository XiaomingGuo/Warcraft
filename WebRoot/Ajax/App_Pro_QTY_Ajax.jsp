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
	String pro_type = (String)request.getParameter("product_type");
	String storageName = (String)request.getParameter("storage");
	if(storageName == null||storageName == "")
	{
		storageName = "other_storage";
	}
	String sql= "select Bar_Code from product_info where name='"+pro_name+"' and product_type='"+ pro_type + "'";
	if (hDBHandle.QueryDataBase(sql))
	{
		barcodeList = hDBHandle.GetAllStringValue("Bar_Code");
	}
	else
	{
		hDBHandle.CloseDatabase();
	}
	for (int i = 0; i < barcodeList.size(); i++)
	{
		String bar_Code = barcodeList.get(i);
		iRepertory += hDBHandle.GetIN_QTYByBarCode(bar_Code, storageName) - hDBHandle.GetOUT_QTYByBarCode(bar_Code, storageName);
		rtnRst += bar_Code + "$";
	}
	rtnRst += Integer.toString(iRepertory);
	out.write(rtnRst);
%>
