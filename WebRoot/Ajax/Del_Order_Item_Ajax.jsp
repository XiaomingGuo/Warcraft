<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String pro_id = (String)request.getParameter("product_id");
	String Order_Name = (String)request.getParameter("Order_Name");
	String Bar_Code = (String)request.getParameter("Bar_Code");
	
	if (pro_id != null)
	{
		String sql = "delete from product_order_record where id='" + pro_id + "'";
		hDBHandle.execUpate(sql);
		sql = "delete from mb_material_po where po_name='" + Order_Name + "' and Bar_Code='" + Bar_Code + "'";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
