<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.DB.operation.Other_Storage" %>
<%@ page import="com.DB.operation.Other_Record" %>
<%@ page import="com.DB.operation.EarthquakeManagement" %>
<%@ page import="com.jsp.support.ApproveAjax" %>
<%
	String rtnRst = "";
	String barcode = (String)request.getParameter("Barcode").replace(" ", "");
	String recordID = (String)request.getParameter("material_id").replace(" ", "");
	int used_count = Integer.parseInt((String)request.getParameter("OUT_QTY").replace(" ", ""));
	ApproveAjax hPageHandle = new ApproveAjax();
	Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
	int repertory_count = hPageHandle.GetStorageRepertory(barcode, Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	if (repertory_count >= used_count)
	{
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		List<List<String>> material_info_List = hPageHandle.GetStorageRecordList(barcode, keyArray);
		
		for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
		{
			String batchLot =  material_info_List.get(0).get(iCol);
			int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
			int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
			int recordCount = sql_in_count - sql_out_count;
			if (recordCount >= used_count)
			{
				hPageHandle.UpdateStorageOutQty(Integer.toString(sql_out_count+used_count), barcode, batchLot);
				hORHandle.UpdateRecordByKeyList("Batch_Lot", batchLot, Arrays.asList("id"), Arrays.asList(recordID));
				hORHandle.UpdateRecordByKeyList("QTY", Integer.toString(used_count), Arrays.asList("id"), Arrays.asList(recordID));
				hORHandle.UpdateRecordByKeyList("isApprove", "1", Arrays.asList("id"), Arrays.asList(recordID));
				hORHandle.UpdateRecordByKeyList("Merge_Mark", recordID, Arrays.asList("id"), Arrays.asList(recordID));
				break;
			}
			else
			{
				hPageHandle.UpdateStorageOutQty(Integer.toString(sql_in_count), barcode, batchLot);
				String[] keyWord = {"proposer", "user_name"};
				List<List<String>> tempList = hPageHandle.GetOtherRecordList(recordID, keyWord);
				List<String> keyList = Arrays.asList("Bar_Code", "proposer", "QTY", "user_name", "isApprove", "Merge_Mark");
				List<String> valList = Arrays.asList(barcode, tempList.get(0).get(0), Integer.toString(recordCount), tempList.get(1).get(0), "0", recordID);
				hORHandle.AddARecord(barcode, tempList.get(0).get(0), Integer.toString(recordCount), tempList.get(1).get(0), recordID);
				hORHandle.UpdateRecordByKeyList("Batch_Lot", batchLot, keyList, valList);
				hORHandle.UpdateRecordByKeyList("QTY", Integer.toString(recordCount), keyList, valList);
				hORHandle.UpdateRecordByKeyList("isApprove", "1", keyList, valList);
				hORHandle.UpdateRecordByKeyList("Merge_Mark", recordID, keyList, valList);
				used_count -= recordCount;
			}
		}
	}
	else
	{
		rtnRst = "("+ barcode + "): 库存数量不足!";
	}

	out.write(rtnRst);
%>
