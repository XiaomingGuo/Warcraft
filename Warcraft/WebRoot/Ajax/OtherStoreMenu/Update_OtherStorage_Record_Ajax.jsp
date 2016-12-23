<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.QueryOther" %>
<%
	String rtnRst = "remove$";
	String recordID = (String)request.getParameter("ID").replace(" ", "");
	String barcode = (String)request.getParameter("Barcode").replace(" ", "");
	String singlePrice = (String)request.getParameter("SinglePrice").replace(" ", "");
	String totalPrice = (String)request.getParameter("TotalPrice").replace(" ", "");
	String vendorName = (String)request.getParameter("VendorName").replace(" ", "");
	
	if (!recordID.contains("...")&&!barcode.contains("...")&&!singlePrice.contains("...")&&!totalPrice.contains("...")&&!vendorName.contains("请选择"))
	{
		QueryOther hPageHandle = new QueryOther();
		hPageHandle.UpdateOtherStorageTableRecordByKeyList("Bar_Code", barcode, Arrays.asList("id"), Arrays.asList(recordID));
		hPageHandle.UpdateOtherStorageTableRecordByKeyList("Price_Per_Unit", singlePrice, Arrays.asList("id"), Arrays.asList(recordID));
		hPageHandle.UpdateOtherStorageTableRecordByKeyList("Total_Price", totalPrice, Arrays.asList("id"), Arrays.asList(recordID));
		hPageHandle.UpdateOtherStorageTableRecordByKeyList("vendor_name", vendorName, Arrays.asList("id"), Arrays.asList(recordID));
	}
	else
		rtnRst += "error:这条记录不存在, 请检查是否确认入库!";
	out.write(rtnRst);
%>
