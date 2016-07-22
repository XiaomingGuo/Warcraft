<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Process_Detail_Info" %>
<%
	String rtnRst = "remove$";
	String barcode = (String)request.getParameter("Bar_Code").replace(" ", "");
	Query_Process_Detail_Info hPageHandle = new Query_Process_Detail_Info();
	
	if (barcode != null)
	{
		if(hPageHandle.IsProductInfoExist(barcode))
			rtnRst += "1$";
		else
			rtnRst += "0$";
	}
	out.write(rtnRst);
%>
