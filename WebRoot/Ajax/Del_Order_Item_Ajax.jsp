<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id").replace(" ", "");
	String Order_Name = (String)request.getParameter("Order_Name").replace(" ", "");
	String Bar_Code = (String)request.getParameter("Bar_Code").replace(" ", "");
	
	if (pro_id != null)
	{
		String sql = "delete from product_order_record where id='" + pro_id + "'";
		hDBHandle.execUpate(sql);
		sql = "delete from mb_material_po where po_name='" + Order_Name + "' and Bar_Code='" + hDBHandle.GetUsedBarcode(Bar_Code, "mb_material_po") + "'";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
