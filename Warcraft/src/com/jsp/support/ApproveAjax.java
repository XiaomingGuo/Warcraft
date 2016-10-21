package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class ApproveAjax extends PageParentClass
{
	public void AddNewOtherRecord(String barcode, String proposerName, String recordCount, String userName, String mergeMark)
	{
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		((Other_Record)hORHandle.getTableInstance()).AddARecord(barcode, proposerName, recordCount, userName, mergeMark);
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
	
	public List<List<String>> GetStorageRecordList(String barcode, String[] keyArray)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandle(barcode);

		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code"), Arrays.asList(barcode));
		for(int idx=0; idx < keyArray.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
		}
		return rtnRst;
	}
	
	public List<List<String>> GetOtherRecordList(String keyVal, String[] keyArray)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = new DatabaseStore("Other_Record");
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(keyVal));
		for(int idx=0; idx < keyArray.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
		}
		return rtnRst;
	}
	
	public void UpdateStorageOutQty(String outQty, String barcode, String batchLot)
	{
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.UpdateRecordByKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
}
