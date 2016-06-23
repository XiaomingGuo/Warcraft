<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Query_Add_Material_PO_Item_Ajax" %>
<%
	String rtnRst = "";
	String po_name = request.getParameter("po_name").replace(" ", "");
	if(po_name.length() > 6)
	{
		Query_Add_Material_PO_Item_Ajax hPageHandle = new Query_Add_Material_PO_Item_Ajax();
		rtnRst += hPageHandle.GenerateReturnString(po_name);
	}
	else
	{
		rtnRst += "remove$error:产品订单号稍微复杂点儿行不?";
	}
	out.write(rtnRst);
%>
