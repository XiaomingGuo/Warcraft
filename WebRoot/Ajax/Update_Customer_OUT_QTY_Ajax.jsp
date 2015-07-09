<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
%>
<%
	String rtnRst = "remove$";
	String appBarcode = (String)request.getParameter("Bar_Code");
	String appPONname = (String)request.getParameter("po_name");
	String out_QTY = request.getParameter("OUT_QTY");
	
	if (appBarcode != null && appPONname != null && out_QTY != null)
	{
		String writeQTY = Integer.toString(Integer.parseInt(hDBHandle.GetPOInfo(appBarcode, appPONname, "OUT_QTY")) + Integer.parseInt(out_QTY));
		String sql = "UPDATE customer_po_record SET OUT_QTY='" + writeQTY + "' where Bar_Code='" + appBarcode + "' and po_name='" + appPONname + "'";
		hDBHandle.execUpate(sql);
	}
	out.write(rtnRst);
%>
