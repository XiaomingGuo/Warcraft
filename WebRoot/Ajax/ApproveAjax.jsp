<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
%>
<%
	String userName="", rtnRst = "";
	String barcode = (String)request.getParameter("Barcode");
	String recordID = (String)request.getParameter("material_id");
	int used_count = Integer.parseInt((String)request.getParameter("OUT_QTY"));
	int repertory_count = hDBHandle.GetRepertoryByBarCode(barcode, "other_storage");
	if (repertory_count >= used_count)
	{
		String sql = "select * from other_storage where Bar_Code='" + barcode +"'";
		if (hDBHandle.QueryDataBase(sql) && hDBHandle.GetRecordCount() > 0)
		{
			List<List<String>> material_info_List = hDBHandle.GetAllDBColumnsByList(keyArray);
			for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
			{
				String batchLot =  material_info_List.get(0).get(iCol);
				int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
				int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
				int recordCount = sql_in_count - sql_out_count;
				if (recordCount >= used_count)
				{
					if (recordCount == used_count)
					{
						hDBHandle.MoveMaterialToExhaustedTable(barcode, batchLot);
					}
					else
					{
						sql= "UPDATE other_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + barcode +"' and Batch_Lot='" + batchLot +"'";
						hDBHandle.execUpate(sql);
					}
					sql= "UPDATE other_record SET Batch_Lot='"+ batchLot +"', QTY='" + Integer.toString(used_count) + "', isApprove='1', Merge_Mark='" + recordID + "' WHERE id='" + recordID + "'";
					hDBHandle.execUpate(sql);
					break;
				}
				else
				{
					if (!hDBHandle.MoveMaterialToExhaustedTable(barcode, batchLot))
						continue;
					sql = "SELECT proposer FROM material_record WHERE id='" + recordID + "'";
					if (hDBHandle.QueryDataBase(sql))
					{
						userName = hDBHandle.GetSingleString("proposer");
						sql = "INSERT INTO material_record (Bar_Code, Batch_Lot, proposer, QTY, isApprove, Merge_Mark) VALUES ('" + barcode + "', '" + batchLot + "', '" + userName + "', '" + Integer.toString(recordCount) + "', '1', '" + recordID + "')";
						hDBHandle.execUpate(sql);
						used_count -= recordCount;
					}
					else
					{
						hDBHandle.CloseDatabase();
					}
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
			rtnRst = "数据库不存在物料("+ barcode + ")的信息";
		}
	}
	else
	{
		rtnRst = "("+ barcode + "): 库存数量不足!";
	}

	out.write(rtnRst);
%>
