<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Submit_Reject_Storage_Qty_Ajax" %>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code");
	String recordId = request.getParameter("recordId");
	
	if (barcode != null||barcode == "")
	{
		Submit_Reject_Storage_Qty_Ajax hPageHandle = new Submit_Reject_Storage_Qty_Ajax();
		hPageHandle.EnsureProductStorageStatusByIdAndBarcode(recordId, barcode);
	}
	out.write(rtnRst);
%>
