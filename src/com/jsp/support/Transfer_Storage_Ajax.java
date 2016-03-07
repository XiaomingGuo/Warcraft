package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Transfer_Storage_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] colNames = {"Bar_Code", "QTY", "percent"};
			for(int idx=0; idx < colNames.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(colNames[idx]));
			}
		}
		return rtnRst;
	}
	
	private void UpdateARecordPoName(String barcode, String batchLot, String poName)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		((ITableInterface)hHandle).UpdateRecordByKeyList("po_name", poName, Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
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
					UpdateARecordPoName(curBarcode, curBatchLot, POName);
					rtnRst -= icurRepertory;
				}
				else
				{
					UpdateARecordPoName(curBarcode, SplitStorageRecord(curBarcode, curBatchLot, rtnRst), POName);
					break;
				}
			}
		}
		return rtnRst;
	}

	private String SplitStorageRecord(String curBarcode, String curBatchLot, int usedQty)
	{
		String rtnRst = null;
		IStorageTableInterface hHandle = GenStorageHandle(curBarcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "isEnsure"), Arrays.asList(curBarcode, curBatchLot, "1"));
		rtnRst = GenBatchLot(curBatchLot.split("-")[0], curBarcode);
		double pricePerUnit = Double.parseDouble(hHandle.getDBRecordList("Price_Per_Unit").get(0));
		String vendor = hHandle.getDBRecordList("vendor_name").get(0);
		String inStoreDate = hHandle.getDBRecordList("in_store_date").get(0);
		hHandle.AddARecord(curBarcode, rtnRst, Integer.toString(usedQty), Double.toString(pricePerUnit), Double.toString(pricePerUnit*usedQty), "empty", "Material_Supply", vendor, inStoreDate);
		((ITableInterface)hHandle).UpdateRecordByKeyList("isEnsure", hHandle.getDBRecordList("isEnsure").get(0), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(curBarcode, rtnRst));
		int oriInQty = Integer.parseInt(hHandle.getDBRecordList("IN_QTY").get(0));
		((ITableInterface)hHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(oriInQty-usedQty), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(curBarcode, curBatchLot));
		return rtnRst;
	}

	public List<List<String>> GetStorageRecordList(String barcode, String storageName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(hHandle.GetUsedBarcode(barcode, storageName), "Material_Supply", "1"));
		String[] KeywordList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY"};
		for(int idx=0; idx < KeywordList.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(KeywordList[idx]));
		}
		return rtnRst;
	}
	
	public void EnsureCustomerPoRecordInput(String POName)
	{
		List<List<String>> recordList = GetAllCustomerPoRecord(POName);
		if(recordList.size() > 0)
		{
			//{"Bar_Code", "vendor"};
			Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
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
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
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
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(POName));
		if(hCPHandle.RecordDBCount() <= 0)
			hCPHandle.AddARecord(POName);
	}
}
