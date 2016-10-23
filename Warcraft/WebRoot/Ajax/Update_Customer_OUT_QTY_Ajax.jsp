<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jsp.support.Update_Customer_OUT_QTY_Ajax" %>
<%
	String rtnRst = "remove$";
	String appBarcode = (String)request.getParameter("Bar_Code").replace(" ", "");
	String appPONum = (String)request.getParameter("po_name").replace(" ", "");
	int used_count = Integer.parseInt(request.getParameter("OUT_QTY").replace(" ", ""));
	//product_type Database query
	if (appBarcode != null && appPONum != null && used_count > 0 && appBarcode.length() == 8 && appPONum.length() > 0)
	{
		Update_Customer_OUT_QTY_Ajax hPageHandle = new Update_Customer_OUT_QTY_Ajax();
		
		String proBarcode = hPageHandle.GetUsedBarcode(appBarcode, "product_storage");
		int repertory_count = hPageHandle.GetAllRepertoryByPOName(proBarcode, appPONum);
		if (repertory_count >= used_count)
		{
			int saveCount = used_count;
			List<List<String>> recordList = hPageHandle.GetStorageRecordList(proBarcode, appPONum);
			if (recordList.size() > 0&&saveCount > 0)
			{
				saveCount = hPageHandle.UpdateShippingRecord(recordList, proBarcode, appPONum, saveCount);
			}
			recordList = hPageHandle.GetProductOtherPoNotDepleteRecord(proBarcode);
			if (recordList.size() > 0&&saveCount > 0)
			{
				saveCount = hPageHandle.UpdateShippingRecord(recordList, proBarcode, appPONum, saveCount);
			}
			recordList = hPageHandle.GetStorageRecordList(proBarcode, "Material_Supply");
			if (recordList.size() > 0&&saveCount > 0)
			{
				saveCount = hPageHandle.UpdateShippingRecord(recordList, proBarcode, appPONum, saveCount);
			}
			hPageHandle.UpdateCustomerPoRecord(proBarcode, appPONum, used_count-saveCount);
			response.sendRedirect("../Product_Shipment.jsp");
		}
		else
		{
			session.setAttribute("error", "("+ appBarcode + "): 库存数量不足,不够出货数量,加油生产吧兄弟!");
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
