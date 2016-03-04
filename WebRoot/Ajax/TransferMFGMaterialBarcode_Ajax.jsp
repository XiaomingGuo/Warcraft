<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="com.DB.operation.Shipping_Record"%>
<%@	page import="com.DB.operation.Customer_Po_Record"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.TransferMFGMaterialBarcode_Ajax" %>
<%
	String rtnRst = "remove$";
/*	{"store_name":from_store_name,
	"from_bar_code":GetSelectedContent("from_bar_code"), "from_bar_code":GetSelectedContent("from_bar_code"),
	"from_QTY":$("#from_QTY").val(), "to_QTY":$("#to_QTY").val()}*/
	String store_name = (String)request.getParameter("store_name").replace(" ", "");
	String from_barcode = (String)request.getParameter("from_bar_code").replace(" ", "");
	String to_barcode = (String)request.getParameter("to_bar_code").replace(" ", "");
	int from_QTY = Integer.parseInt(request.getParameter("from_QTY").replace(" ", ""));
	int to_QTY = Integer.parseInt(request.getParameter("to_QTY").replace(" ", ""));
	//product_type Database query
	if (store_name != null && from_barcode != null && to_barcode != null && from_QTY > 0 && to_QTY > 0 && from_barcode.length() == 8 && to_barcode.length() == 8)
	{
		TransferMFGMaterialBarcode_Ajax hPageHandle = new TransferMFGMaterialBarcode_Ajax();
		
		int repertory_count = hPageHandle.GetAllNotDepleteRepertory(from_barcode);
		if (repertory_count >= from_QTY)
		{
			List<List<String>> recordList = hPageHandle.GetAllNotDepleteRecordList(from_barcode);
			if (recordList.size() > 0&&from_QTY > 0)
			{
				hPageHandle.UpdateStorageRecord(recordList, from_barcode, from_QTY, to_barcode, to_QTY);
			}
		}
		else
		{
			session.setAttribute("error", "("+ from_barcode + "): 库存数量不足,不够出货数量,加油催料吧兄弟!");
			response.sendRedirect("../tishi.jsp");
		}
	}
	else
	{
		session.setAttribute("error", "你输入的是什么啊,赶紧重新输入!");
		response.sendRedirect("../tishi.jsp");
	}

	out.write(rtnRst);
%>
