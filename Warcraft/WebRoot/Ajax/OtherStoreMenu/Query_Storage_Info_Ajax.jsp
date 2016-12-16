<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryStorageItemAjax" %>
<%
	String rtnRst = "remove$";
	String storage_name = request.getParameter("storage_name");
	String product_type = request.getParameter("product_type");
	if(!storage_name.contains("请选择")&&!product_type.contains("请选择"))
	{
		QueryStorageItemAjax hPageHandle = new QueryStorageItemAjax();
		rtnRst += hPageHandle.GenerateStorageInfoString(storage_name, product_type);
	}
	else
        rtnRst += "error:必须选定库存和产品类型!";
	out.write(rtnRst);
%>
