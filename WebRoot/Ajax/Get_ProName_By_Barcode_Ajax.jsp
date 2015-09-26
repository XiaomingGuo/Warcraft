<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code").replace(" ", "");
	if(barcode.length() == 8)
	{
		String proType = hDBHandle.GetTypeByBarcode(barcode);
		String storeroom = hDBHandle.GetStoreroomByType(proType);
		String proName = hDBHandle.GetNameByBarcode(barcode);
		if (!proType.isEmpty() && !proName.isEmpty())
		{
			rtnRst += storeroom + "$";
			rtnRst += proType + "$";
			rtnRst += proName + "$";
		}
		else
		{
			rtnRst += "error:这个八码不存在?";
		}
	}
	else
	{
		rtnRst += "error:八码有八位你不知道吗?";
	}
	out.write(rtnRst);
%>
