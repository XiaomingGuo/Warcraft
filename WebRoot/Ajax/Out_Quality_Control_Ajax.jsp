<%@page import="com.DB.operation.Material_Storage"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.Out_Quality_Control_Ajax" %>
<%
	String rtnRst = "remove$";
	String pro_id = request.getParameter("product_id").replace(" ", "");
	String QTYOfStore = request.getParameter("PutInQTY").replace(" ", "");
	String sql = "";
	
	if (pro_id != null && QTYOfStore != null)
	{
		int used_count = Integer.parseInt(QTYOfStore);
		String[] orderRecordKey = {"Bar_Code", "Order_Name", "QTY", "completeQTY", "OQC_QTY"};
		Out_Quality_Control_Ajax hPageHandle = new Out_Quality_Control_Ajax();
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(pro_id));
		List<List<String>> orderInfo = new ArrayList<List<String>>();
		for(int idx=0; idx < orderRecordKey.length; idx++)
		{
			orderInfo.add(hPORHandle.getDBRecordList(orderRecordKey[idx]));
		}
		
		if (orderInfo.size() > 0)
		{
			String barcode = orderInfo.get(0).get(0);
			String ordername = orderInfo.get(1).get(0);
			int iOrder_QTY = Integer.parseInt(orderInfo.get(2).get(0));
			int complete_QTY = Integer.parseInt(orderInfo.get(3).get(0));
			int pro_record_comp_QTY = Integer.parseInt(orderInfo.get(4).get(0));
			if (complete_QTY >= pro_record_comp_QTY + used_count)
			{
				hPORHandle.UpdateRecordByKeyList("OQC_QTY", Integer.toString(pro_record_comp_QTY + used_count), Arrays.asList("id"), Arrays.asList(pro_id));
				if (iOrder_QTY == (pro_record_comp_QTY+used_count))
				{
					hPORHandle.UpdateRecordByKeyList("status", "5", Arrays.asList("id"), Arrays.asList(pro_id));
				}
				else
				{
					hPORHandle.UpdateRecordByKeyList("status", "4", Arrays.asList("id"), Arrays.asList(pro_id));
				}
				
				String[] materialKey = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
				Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
				hMSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
				List<List<String>> material_info_List = new ArrayList<List<String>>();
				for(int idx=0; idx < materialKey.length; idx++)
				{
					material_info_List.add(hMSHandle.getDBRecordList(materialKey[idx]));
				}
				if (material_info_List.size() > 0)
				{
					for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
					{
						String batchLot =  material_info_List.get(0).get(iCol);
						int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
						int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
						int recordCount = sql_in_count - sql_out_count;
					
						if (recordCount >= used_count)
						{
							hPageHandle.UpdateStorageOutQty(Integer.toString(sql_out_count+used_count), hMSHandle.GetUsedBarcode(barcode, "material_storage"), batchLot);
							hPageHandle.TransferMaterialToProduct(barcode, batchLot, ordername, used_count);
							break;
						}
						else
						{
							hPageHandle.UpdateStorageOutQty(Integer.toString(sql_in_count), hMSHandle.GetUsedBarcode(barcode, "material_storage"), batchLot);
							hPageHandle.TransferMaterialToProduct(barcode, batchLot, ordername, recordCount);
							used_count -= recordCount;
						}
					}
				}
			}
			else
			{
				rtnRst += "error:已完成的数量都不够你检验!";
			}
		}
	}
	out.write(rtnRst);
%>
