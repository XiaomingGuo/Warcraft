<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String barcode = null, ordername = null;
%>
<%
	String rtnRst = "remove$";
	String pro_id = request.getParameter("product_id");
	String QTYOfStore = request.getParameter("PutInQTY");
	
	if (pro_id != null && QTYOfStore != null)
	{
		int used_count = Integer.parseInt(QTYOfStore);
		String sql = "select * from product_order_record where id='" + pro_id + "'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			String[] orderRecordKey = {"Bar_Code", "Order_Name", "QTY", "completeQTY"};
			List<List<String>> orderInfo = hDBHandle.GetAllDBColumnsByList(orderRecordKey);
			barcode = orderInfo.get(0).get(0);
			ordername = orderInfo.get(1).get(0);
			int order_QTY = Integer.parseInt(orderInfo.get(2).get(0));
			int pro_record_comp_QTY = Integer.parseInt(orderInfo.get(3).get(0));
			
			sql = "select * from material_storage where Bar_Code='" + Integer.toString(Integer.parseInt(barcode)-10000000) + "'";
			String[] materialKey = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
			if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
			{
				if (order_QTY == (pro_record_comp_QTY+used_count))
				{
					sql= "UPDATE product_order_record SET completeQTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='3' WHERE id='" + pro_id + "'";
				}
				else
				{
					sql= "UPDATE product_order_record SET completeQTY='"+ Integer.toString(pro_record_comp_QTY + used_count) + "', status='1' WHERE id='" + pro_id + "'";
				}
				hDBHandle.execUpate(sql);

				List<List<String>> material_info_List = hDBHandle.GetAllDBColumnsByList(materialKey);
				for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
				{
					String batchLot =  material_info_List.get(0).get(iCol);
					int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
					int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
					int recordCount = sql_in_count - sql_out_count;
				
					if (recordCount >= used_count)
					{
						sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + Integer.toString(Integer.parseInt(barcode)-10000000) +"' and Batch_Lot='" + batchLot +"'";
						hDBHandle.execUpate(sql);
						if (recordCount == used_count)
						{
							hDBHandle.MoveToExhaustedTable(Integer.toString(Integer.parseInt(barcode)-10000000), batchLot, "material_storage", "exhausted_material");
						}
						hDBHandle.TransferMaterialToProduct(barcode, batchLot, ordername, used_count);
						break;
					}
					else
					{
						if (!hDBHandle.MoveToExhaustedTable(Integer.toString(Integer.parseInt(barcode)-10000000), batchLot, "material_storage", "exhausted_material"))
							continue;
						hDBHandle.TransferMaterialToProduct(barcode, batchLot, ordername, recordCount);
						used_count -= recordCount;
					}
				}
			}
		}
	}
	
	out.write(rtnRst);
%>
