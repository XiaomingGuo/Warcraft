<%@page import="org.apache.struts2.components.Else"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String proName = request.getParameter("search_name");
	String flag = request.getParameter("flag");
	if(proName.length() > 0)
	{
		String barcode = hDBHandle.GetBarcodeByName(proName, flag);
		if (!barcode.isEmpty())
		{
			rtnRst += barcode + "$";
		}
		else
		{
			rtnRst += "error:八码为空?";
		}
	}
	else
	{
		rtnRst += "error:名称不存在?";
	}
	out.write(rtnRst);
%>
