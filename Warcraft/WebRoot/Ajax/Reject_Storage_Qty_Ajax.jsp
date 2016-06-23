<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Submit_Reject_Storage_Qty_Ajax" %>
<%
	String rtnRst = "remove$";
	String barcode = request.getParameter("Bar_Code");
	String batchLot = request.getParameter("Batch_Lot");
	
	if (barcode != null||barcode == "")
	{
		Submit_Reject_Storage_Qty_Ajax hPageHandle = new Submit_Reject_Storage_Qty_Ajax();
		hPageHandle.DeleteRecordByBarcodeAndBatchLot(barcode, batchLot);
	}
	out.write(rtnRst);
%>
