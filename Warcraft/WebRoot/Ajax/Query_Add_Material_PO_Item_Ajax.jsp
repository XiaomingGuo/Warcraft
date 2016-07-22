<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.AddMFGMaterial_ReferTo_PO" %>
<%
	String rtnRst = "";
	String po_name = request.getParameter("po_name").replace(" ", "");
	if(po_name.length() > 6)
	{
		AddMFGMaterial_ReferTo_PO hPageHandle = new AddMFGMaterial_ReferTo_PO();
		rtnRst += hPageHandle.GenerateReturnString(po_name);
	}
	else
	{
		rtnRst += "remove$error:产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
