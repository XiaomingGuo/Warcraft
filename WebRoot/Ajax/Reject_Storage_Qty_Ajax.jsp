<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Material_Storage" %>
<%@ page import="com.DB.operation.Other_Storage" %>
<%@ page import="com.DB.operation.Product_Storage" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.Warcraft.Interface.ITableInterface"%>
<%@ page import="com.jsp.support.Submit_Reject_Storage_Qty_Ajax" %>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code");
	String recordId = request.getParameter("recordId");
	
	if (barcode != null||barcode == "")
	{
		Submit_Reject_Storage_Qty_Ajax hPageHandle = new Submit_Reject_Storage_Qty_Ajax();
		hPageHandle.DeleteRecordByBarcodeAndId(recordId, barcode);
	}
	out.write(rtnRst);
%>
