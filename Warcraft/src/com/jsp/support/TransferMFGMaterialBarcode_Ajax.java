package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.*;

public class TransferMFGMaterialBarcode_Ajax extends PageParentClass
{
	public int GetAllNotDepleteRepertory(String barcode)
	{
		int rtnRst = 0;
		DBTableParent hHandle = GenStorageHandle(barcode);
		((DBTableParent)hHandle).QueryRecordByFilterKeyListGroupByList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcode, "1"), Arrays.asList("po_name"));
		List<String> loopList = hHandle.getDBRecordList("po_name");
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		for (String poName : loopList)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
			if(poName.equals("Material_Supply")||Integer.parseInt(hCPHandle.getDBRecordList("status").get(0)) >= 5)
			{
				rtnRst += ((DBTableParent)hHandle).GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, poName, "1"));
			}
		}
		return rtnRst;
	}
	
	public List<List<String>> GetAllNotDepleteRecordList(String barcode)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandle(barcode);
		((DBTableParent)hHandle).QueryRecordByFilterKeyListGroupByList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcode, "1"), Arrays.asList("po_name"));
		List<String> loopList = hHandle.getDBRecordList("po_name");
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name", "po_name"};
		for (String poName : loopList)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
			if(poName.equals("Material_Supply")||Integer.parseInt(hCPHandle.getDBRecordList("status").get(0)) >= 5)
			{
				hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, poName, "1"));
				for(int idx=0; idx < keyArray.length; idx++)
				{
					if(keyArray.length > rtnRst.size())
						rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
					else
						rtnRst.get(idx).addAll(hHandle.getDBRecordList(keyArray[idx]));
				}
			}
		}
		return rtnRst;
	}
	
	public void UpdateStorageRecord(List<List<String>> recordList, String from_barcode, int from_QTY, String to_barcode, int to_QTY, int saveFromFlag)
	{
		int used_count = from_QTY;
		String to_Batch_Lot = GenBatchLot(to_barcode);
		DBTableParent hTBRHandle = new DatabaseStore("Trans_Barcode_Record");
		for (int iCol = 0; iCol < recordList.get(0).size(); iCol++)
		{
			String batchLot = recordList.get(0).get(iCol);
			int sql_in_count = Integer.parseInt(recordList.get(1).get(iCol));
			int sql_out_count = Integer.parseInt(recordList.get(2).get(iCol));
			int recordCount = sql_in_count - sql_out_count;
			if (recordCount >= used_count)
			{
				String updateQty = Integer.toString(sql_in_count-used_count);
				UpdateStorageInQty(updateQty, from_barcode, batchLot);
				((Trans_Barcode_Record)hTBRHandle.getTableInstance()).AddARecord(from_barcode, batchLot, Integer.toString(used_count), to_barcode, to_Batch_Lot, Integer.toString(to_QTY));
				break;
			}
			else
			{
				String updateQty = Integer.toString(sql_out_count);
				UpdateStorageInQty(updateQty, from_barcode, batchLot);
				((Trans_Barcode_Record)hTBRHandle.getTableInstance()).AddARecord(from_barcode, batchLot, Integer.toString(recordCount), to_barcode, to_Batch_Lot, Integer.toString(to_QTY));
				used_count -= recordCount;
			}
		}
		DBTableParent hHandle = GenStorageHandle(to_barcode);
		AddSingleOtherManuRecordToStorage(hHandle, to_barcode, to_Batch_Lot, Integer.toString(to_QTY), "0", "0", "empty", "Material_Supply", "MB_Incise_Station", GenYearMonthDayString());
		hHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(to_barcode, to_Batch_Lot));
	}
	
	public void UpdateStorageInQty(String inQty, String barcode, String batchLot)
	{
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.UpdateRecordByKeyList("IN_QTY", inQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
}
