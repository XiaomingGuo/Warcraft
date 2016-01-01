<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Storage" %>
<%@	page import="com.DB.operation.Shipping_Record"%>
<%@	page import="com.DB.operation.Customer_Po_Record"%>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Update_Customer_OUT_QTY_Ajax" %>
<%
	String rtnRst = "remove$";
	String appBarcode = (String)request.getParameter("Bar_Code").replace(" ", "");
	String appPONum = (String)request.getParameter("po_name").replace(" ", "");
	String out_QTY = request.getParameter("OUT_QTY").replace(" ", "");
	int used_count = Integer.parseInt(out_QTY);
	//product_type Database query
	if (appBarcode != null && appPONum != null && out_QTY != null && used_count > 0 && appBarcode.length()==8 && appPONum!="")
	{
		Update_Customer_OUT_QTY_Ajax hPageHandle = new Update_Customer_OUT_QTY_Ajax();
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		Shipping_Record hSRHandle = new Shipping_Record(new EarthquakeManagement());
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		int repertory_count = hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		if (repertory_count >= used_count)
		{
			hPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPSHandle.GetUsedBarcode(appBarcode, "product_storage")));
			if (hPSHandle.RecordDBCount() > 0)
			{
				String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name"};
				List<List<String>> material_info_List = new ArrayList<List<String>>();
				for(int idx=0; idx < keyArray.length; idx++)
				{
					material_info_List.add(hPSHandle.getDBRecordList(keyArray[idx]));
				}
				for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
				{
					String batchLot =  material_info_List.get(0).get(iCol);
					int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
					int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
					String ordername = material_info_List.get(3).get(iCol);
					int recordCount = sql_in_count - sql_out_count;
					if (recordCount >= used_count)
					{
						hPageHandle.UpdateStorageOutQty(Integer.toString(sql_out_count+used_count), hPSHandle.GetUsedBarcode(appBarcode, "product_storage"), batchLot);
						hSRHandle.AddARecord(appPONum, hSRHandle.GetUsedBarcode(appBarcode, "shipping_record"), batchLot, ordername, Integer.toString(used_count));
						break;
					}
					else
					{
						hPageHandle.UpdateStorageOutQty(Integer.toString(sql_in_count), hPSHandle.GetUsedBarcode(appBarcode, "product_storage"), batchLot);
						hSRHandle.AddARecord(appPONum, hSRHandle.GetUsedBarcode(appBarcode, "shipping_record"), batchLot, ordername, Integer.toString(recordCount));
						used_count -= recordCount;
					}
				}
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(appBarcode, "shipping_record"), appPONum));
				String writeQTY = Integer.toString(Integer.parseInt(hCPRHandle.getDBRecordList("OUT_QTY").get(0)) + Integer.parseInt(out_QTY));
				hCPRHandle.UpdateRecordByKeyList("OUT_QTY", writeQTY, Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(appBarcode, "shipping_record"), appPONum));
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
