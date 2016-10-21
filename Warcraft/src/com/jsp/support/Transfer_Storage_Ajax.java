package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class Transfer_Storage_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] colNames = {"Bar_Code", "QTY", "percent"};
			for(int idx=0; idx < colNames.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(colNames[idx]));
			}
		}
		return rtnRst;
	}
	
	private void UpdateARecordPoName(String barcode, String batchLot, String POName, int iQty)
	{
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		hHandle.UpdateRecordByKeyList("OUT_QTY", Integer.toString(iQty+Integer.parseInt(hHandle.getDBRecordList("OUT_QTY").get(0))), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		DBTableParent hProcessHandle = GenProcessStorageHandle(barcode);
		AddSingleRecordToStorage(hProcessHandle, barcode, batchLot, Integer.toString(iQty), hHandle.getDBRecordList("Price_Per_Unit").get(0),
				hHandle.getDBRecordList("Total_Price").get(0), hHandle.getDBRecordList("Order_Name").get(0), POName,
				hHandle.getDBRecordList("vendor_name").get(0), hHandle.getDBRecordList("in_store_date").get(0));
		hProcessHandle.UpdateRecordByKeyList("isEnsure", "1", 
				Arrays.asList("Bar_Code", "Batch_Lot", "in_store_date"), 
				Arrays.asList(barcode, batchLot, hHandle.getDBRecordList("in_store_date").get(0)));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
	
	public void UpdateStoragePoName(String strBarcode, String POName, int mbPoQty)
	{
		int tempQty = SetStoragePoName(strBarcode, POName, mbPoQty, "Product_Storage");
		tempQty = SetStoragePoName(strBarcode, POName, tempQty, "Semi_Storage");
		tempQty = SetStoragePoName(strBarcode, POName, tempQty, "Material_Storage");
	}
	
	private int SetStoragePoName(String strBarcode, String POName, int mbPoQty, String storeName)
	{
		int rtnRst = mbPoQty;
		List<List<String>> recordList = GetStorageRecordList(strBarcode, storeName);
		if(recordList.size() > 0)
		{
			for(int idx=0; idx < recordList.get(0).size(); idx++)
			{
				if(0 == rtnRst)
					break;
				String curBarcode = recordList.get(0).get(idx);
				String curBatchLot = recordList.get(1).get(idx);
				int icurRepertory = Integer.parseInt(recordList.get(2).get(idx)) - Integer.parseInt(recordList.get(3).get(idx));
				
				if(icurRepertory <= rtnRst)
				{
					UpdateARecordPoName(curBarcode, curBatchLot, POName, icurRepertory);
					rtnRst -= icurRepertory;
				}
				else
				{
					UpdateARecordPoName(curBarcode, curBatchLot, POName, rtnRst);
					break;
				}
			}
		}
		return rtnRst;
	}
	
	private List<List<String>> GetStorageRecordList(String barcode, String storageName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(hHandle.GetUsedBarcode(barcode, storageName), "1"));
		List<String> tempPoName = hHandle.getDBRecordList("po_name");
		String[] KeywordList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY"};
		for(int keyWordIdx=0; keyWordIdx < KeywordList.length; keyWordIdx++)
		{
			rtnRst.add(new ArrayList<String>());
			List<String> tempRstList = hHandle.getDBRecordList(KeywordList[keyWordIdx]);
			for(int recordIdx=0; recordIdx < tempPoName.size(); recordIdx++)
			{
				if(IsCustomerPoClose(tempPoName.get(recordIdx)))
					rtnRst.get(keyWordIdx).add(tempRstList.get(recordIdx));
			}
		}
		return rtnRst;
	}
	
	public void EnsureCustomerPoRecordInput(String POName)
	{
		List<List<String>> recordList = GetAllCustomerPoRecord(POName);
		if(recordList.size() > 0)
		{
			DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
			for (int idx = 0; idx < recordList.get(0).size(); idx++)
			{
				String barcode = recordList.get(0).get(idx);
				String vendor = recordList.get(1).get(idx);
				hCPRHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "po_name", "vendor"), Arrays.asList(barcode, POName, vendor));
			}
		}
	}

	private List<List<String>> GetAllCustomerPoRecord(String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		String[] getKeyword = {"Bar_Code", "vendor"};
		for (int idx=0; idx < getKeyword.length; idx++)
		{
			rtnRst.add(hCPRHandle.getDBRecordList(getKeyword[idx]));
		}
		return rtnRst;
	}
	
	public void AddCustomerPo(String POName)
	{
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		if(hCPHandle.getTableInstance().RecordDBCount() <= 0)
			((Customer_Po)hCPHandle.getTableInstance()).AddARecord(POName);
	}
	
	public boolean CheckSubmitPo(String POName)
	{
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		if (hCPHandle.getTableInstance().RecordDBCount() > 0)
			return true;
		return false;
	}
}
