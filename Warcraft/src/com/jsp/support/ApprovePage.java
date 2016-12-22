package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class ApprovePage extends PageParentClass
{
	public boolean ApproveApplication(String barcode, String recordID, String usedCount, String applyDate)
	{
		int used_count = Integer.parseInt(usedCount);
		int repertory_count = GetStorageRepertory(barcode, Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		if (repertory_count >= used_count)
		{
			List<List<String>> material_info_List = GetStorageRecordList(barcode);
			
			for (int iCol = 0; iCol < material_info_List.get(0).size(); iCol++)
			{
				String batchLot =  material_info_List.get(0).get(iCol);
				int sql_in_count = Integer.parseInt(material_info_List.get(1).get(iCol));
				int sql_out_count = Integer.parseInt(material_info_List.get(2).get(iCol));
				int recordCount = sql_in_count - sql_out_count;
				if (recordCount >= used_count)
				{
					UpdateStorageOutQty(Integer.toString(sql_out_count+used_count), barcode, batchLot);
					ApproveOtherRecord(Arrays.asList("Batch_Lot", "QTY", "isApprove", "Merge_Mark"),
													Arrays.asList(batchLot, Integer.toString(used_count), "1", recordID),
													Arrays.asList("id"), Arrays.asList(recordID));
					break;
				}
				else
				{
					UpdateStorageOutQty(Integer.toString(sql_in_count), barcode, batchLot);
					
					List<List<String>> tempList = GetOtherRecordList(recordID);
					List<String> keyList = Arrays.asList("Bar_Code", "proposer", "QTY", "user_name", "isApprove", "Merge_Mark");
					List<String> valList = Arrays.asList(barcode, tempList.get(0).get(0), Integer.toString(recordCount), tempList.get(1).get(0), "0", recordID);
					AddNewOtherRecord(barcode, tempList.get(0).get(0), Integer.toString(recordCount), tempList.get(1).get(0),applyDate, recordID);
					ApproveOtherRecord(Arrays.asList("Batch_Lot", "QTY", "isApprove", "Merge_Mark"),
													Arrays.asList(batchLot, Integer.toString(recordCount), "1", recordID),
													keyList, valList);
					used_count -= recordCount;
				}
			}
		}
		else
			return false;
		return true;
	}
	
	public int GetOtherRecordCount()
	{
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		hORHandle.QueryRecordByFilterKeyList(Arrays.asList("isApprove"), Arrays.asList("0"));
		return hORHandle.getTableInstance().RecordDBCount();
	}
	
	public String GetNameByBarCode(String Barcode)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(Barcode));
		return hPIHandle.getDBRecordList("name").get(0);
	}
	
	public List<List<String>> GetDisplayRecordList(int BeginPage, int PageRecordCount)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		hORHandle.QueryRecordByFilterKeyListWithOrderAndLimit(Arrays.asList("isApprove"), Arrays.asList("0"), Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
		if (hORHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"id", "Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "apply_date", "create_date", "isApprove"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hORHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	public void AddNewOtherRecord(String barcode, String proposerName, String recordCount, String userName, String applyDate, String mergeMark)
	{
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		((Other_Record)hORHandle.getTableInstance()).AddARecord(barcode, proposerName, recordCount, userName, applyDate, mergeMark);
	}
	
	public void ApproveOtherRecord(List<String> updateKeyWords, List<String> updateKeyVals, List<String> keyLists, List<String> valueLists)
	{
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		for(int idx = 0; idx < updateKeyWords.size(); idx++)
			hORHandle.UpdateRecordByKeyList(updateKeyWords.get(idx), updateKeyVals.get(idx), keyLists, valueLists);
	}
	
	public int GetStorageRepertory(String barcode)
	{
		return GetQTYByBarCode("IN_QTY", barcode, Arrays.asList("Bar_Code"), Arrays.asList(barcode)) -
				GetQTYByBarCode("OUT_QTY", barcode, Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
	
	public List<List<String>> GetStorageRecordList(String barcode)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandle(barcode);
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY"};
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code"), Arrays.asList(barcode));
		for(int idx=0; idx < keyArray.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
		}
		return rtnRst;
	}
	
	public List<List<String>> GetOtherRecordList(String keyVal)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = new DatabaseStore("Other_Record");
		String[] keyWord = {"proposer", "user_name"};
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(keyVal));
		for(int idx=0; idx < keyWord.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyWord[idx]));
		}
		return rtnRst;
	}
	
	private void UpdateStorageOutQty(String outQty, String barcode, String batchLot)
	{
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.UpdateRecordByKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
	
	public String DeleteOtherApplyRecord(String applyRecordId)
	{
		DBTableParent hHandle = new DatabaseStore("Other_Record");;
		hHandle.DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(applyRecordId));
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(applyRecordId));
		if(hHandle.getTableInstance().RecordDBCount() > 0)
			return "error:Delete Record Fault, please try again!";
		return "";
	}
}
