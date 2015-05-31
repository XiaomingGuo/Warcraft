<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.DatabaseConn" %>
<%!
	DatabaseConn hDBHandle = new DatabaseConn();
	String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
%>
<%
	String rtnRst = "";
	String barcode = (String)request.getParameter("Barcode");
	String recordID = (String)request.getParameter("material_id");
	int used_count = Integer.parseInt((String)request.getParameter("OUT_QTY"));
	int repertory_count = hDBHandle.GetRepertoryByBarCode(barcode);
	if (repertory_count >= used_count)
	{
		String sql = "select * from material_storage where Bar_Code='" + barcode +"'";
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
					sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_out_count+used_count) + "' WHERE Bar_Code='" + barcode +"' and Batch_Lot='" + batchLot +"'";
					hDBHandle.execUpate(sql);
					if (hDBHandle.GetMergeMark(recordID) == "0")
					{
						sql= "UPDATE material_record SET Batch_Lot='"+ batchLot +"' and isApprove='1' WHERE id='" + recordID + "'";
					}
					else
					{
						sql= "INSERT INTO material_record (Bar_Code, Batch_Lot, proposer, QTY, isApprove, Merge_Mark) VALUES ()";
					}
					hDBHandle.execUpate(sql);
					rtnRst = "";
					break;
				}
				else
				{
					sql= "UPDATE material_storage SET OUT_QTY='" + Integer.toString(sql_in_count) + "' WHERE Bar_Code='" + barcode +"' and Batch_Lot='" + batchLot +"'";
					hDBHandle.execUpate(sql);
					
					sql= "INSERT INTO material_record (Bar_Code, Batch_Lot, proposer, QTY, isApprove, Merge_Mark) VALUES ()";;
					hDBHandle.execUpate(sql);
					used_count -= recordCount;
					continue;
				}
			}
		}
		else
		{
			hDBHandle.CloseDatabase();
		}
	}
	else
	{
		rtnRst = (String)request.getParameter("Pro_Name") + ": 数量不足!";
	}

	out.write(rtnRst);
%>
