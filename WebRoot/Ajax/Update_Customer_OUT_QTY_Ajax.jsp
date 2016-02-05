<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	page import="com.DB.operation.Shipping_Record"%>
<%@	page import="com.DB.operation.Customer_Po_Record"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
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
		Shipping_Record hSRHandle = new Shipping_Record(new EarthquakeManagement());
		String proBarcode = hPageHandle.GetUsedBarcode(appBarcode, "product_storage");
		int repertory_count = hPageHandle.GetAStorageRepertoryByPOName(proBarcode, appPONum) + hPageHandle.GetAStorageRepertoryByPOName(proBarcode, "Material_Supply");
		if (repertory_count >= used_count)
		{
			List<List<String>> recordList = hPageHandle.GetStorageRecordList(proBarcode, appPONum);
			if (recordList.size() > 0)
			{
				//{"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name"};
				for (int iCol = 0; iCol < recordList.get(0).size(); iCol++)
				{
					String batchLot =  recordList.get(0).get(iCol);
					int sql_in_count = Integer.parseInt(recordList.get(1).get(iCol));
					int sql_out_count = Integer.parseInt(recordList.get(2).get(iCol));
					String ordername = recordList.get(3).get(iCol);
					int recordCount = sql_in_count - sql_out_count;
					if (recordCount >= used_count)
					{
						hPageHandle.UpdateStorageOutQty(Integer.toString(sql_out_count+used_count), proBarcode, batchLot);
						hSRHandle.AddARecord(appPONum, proBarcode, batchLot, ordername, Integer.toString(used_count));
						break;
					}
					else
					{
						hPageHandle.UpdateStorageOutQty(Integer.toString(sql_in_count), proBarcode, batchLot);
						hSRHandle.AddARecord(appPONum, proBarcode, batchLot, ordername, Integer.toString(recordCount));
						used_count -= recordCount;
					}
				}
				hPageHandle.UpdateCustomerPoRecord(proBarcode, appPONum, used_count);
				response.sendRedirect("../Product_Shipment.jsp");
			}
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
