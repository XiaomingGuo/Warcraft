<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Product_Order_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.DB.core.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String barcode = null, ordername = null;
%>
<%
	String rtnRst = "remove$";
	String pro_id = request.getParameter("product_id").replace(" ", "");
	String QTYOfStore = request.getParameter("PutInQTY").replace(" ", "");
	String sql = "";
	
	if (pro_id != null && QTYOfStore != null)
	{
		int used_count = Integer.parseInt(QTYOfStore);
		String[] orderRecordKey = {"Bar_Code", "Order_Name", "QTY", "completeQTY", "OQC_QTY"};
		List<List<String>> orderInfo = hDBHandle.GetProductOrderRecord(pro_id, orderRecordKey);
		
		if (orderInfo != null)
		{
			barcode = orderInfo.get(0).get(0);
			ordername = orderInfo.get(1).get(0);
			int iOrder_QTY = Integer.parseInt(orderInfo.get(2).get(0));
			int complete_QTY = Integer.parseInt(orderInfo.get(3).get(0));
			int pro_record_comp_QTY = Integer.parseInt(orderInfo.get(4).get(0));
			if (complete_QTY >= pro_record_comp_QTY + used_count)
			{
				if (iOrder_QTY == (pro_record_comp_QTY+used_count))
				{
					sql= "UPDATE product_order_record SET OQC_QTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='5' WHERE id='" + pro_id + "'";
				}
				else
				{
					sql= "UPDATE product_order_record SET OQC_QTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='4' WHERE id='" + pro_id + "'";
				}
				hDBHandle.execUpate(sql);
				
				String[] materialKey = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
				List<List<String>> material_info_List = hDBHandle.GetMaterialStorage(barcode, materialKey);
				if (material_info_List != null)
				{
					for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
					{
						String batchLot =  material_info_List.get(0).get(iCol);
						int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
						int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
						int recordCount = sql_in_count - sql_out_count;
					
						if (recordCount >= used_count)
						{
							sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + hDBHandle.GetUsedBarcode(barcode, "material_storage") +"' and Batch_Lot='" + batchLot +"'";
							hDBHandle.execUpate(sql);
							if (recordCount == used_count)
							{
								hDBHandle.MoveToExhaustedTable(barcode, batchLot, "material_storage", "exhausted_material");
							}
							hDBHandle.TransferMaterialToProduct(barcode, batchLot, ordername, used_count);
							break;
						}
						else
						{
							sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_in_count) + "' WHERE Bar_Code='" + hDBHandle.GetUsedBarcode(barcode, "material_storage") +"' and Batch_Lot='" + batchLot +"'";
							hDBHandle.execUpate(sql);
							if (!hDBHandle.MoveToExhaustedTable(barcode, batchLot, "material_storage", "exhausted_material"))
								continue;
							hDBHandle.TransferMaterialToProduct(barcode, batchLot, ordername, recordCount);
							used_count -= recordCount;
						}
					}
				}
				else
				{
					;
				}
			}
			else
			{
				rtnRst += "error:已完成的数量都不够你检验!";
			}
		}
		else
		{
			;
		}
	}
	
	out.write(rtnRst);
%>
