package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Update_Customer_OUT_QTY_Ajax extends PageParentClass
{
	public List<List<String>> GetStorageRecordList(String barcode, String POName)
	{
		return _GetStorageRecordList(barcode, POName);
	}
	
	private List<List<String>> _GetStorageRecordList(String barcode, String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		IStorageTableInterface hPSHandle = GenStorageHandle(barcode);
		hPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, POName, "1"));
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name"};
		if (hPSHandle.RecordDBCount() > 0)
		{
			for(int idx=0; idx < keyArray.length; idx++)
			{
				rtnRst.add(hPSHandle.getDBRecordList(keyArray[idx]));
			}
		}
		return rtnRst;
	}
	
	public List<List<String>> GetProductOtherPoNotDepleteRecord(String strBarcode)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		hPSHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(strBarcode, "1"), Arrays.asList("po_name"));
		List<String> loopList = hPSHandle.getDBRecordList("po_name");
		String[] keyArray = {"Batch_Lot", "IN_QTY", "OUT_QTY", "Order_Name"};
		for (String poName : loopList)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
			if(Integer.parseInt(hCPHandle.getDBRecordList("status").get(0)) >= 5)
			{
				hPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(strBarcode, poName));
				for(int idx=0; idx < keyArray.length; idx++)
				{
					if(rtnRst.size() != keyArray.length)
						rtnRst.add(hPSHandle.getDBRecordList(keyArray[idx]));
					else
						rtnRst.get(idx).addAll(hPSHandle.getDBRecordList(keyArray[idx]));
				}
			}
		}
		return rtnRst;
	}
	
	public void UpdateCustomerPoRecord(String barcode, String POName, int used_count)
	{
		String[] storageList = new String[] {"Material_Storage", "Product_Storage", "Semi_Pro_Storage"};
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		for(int idx = 0; idx < storageList.length; idx++)
		{
			String curBarcode = GetUsedBarcode(barcode, storageList[idx]);
			hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(curBarcode, POName));
			if(hCPRHandle.RecordDBCount() > 0)
			{
				int inQty = Integer.parseInt(hCPRHandle.getDBRecordList("QTY").get(0));
				int outQty = Integer.parseInt(hCPRHandle.getDBRecordList("OUT_QTY").get(0));
				if(inQty >= outQty + used_count)
				{
					String writeQTY = Integer.toString(outQty + used_count);
					hCPRHandle.UpdateRecordByKeyList("OUT_QTY", writeQTY, Arrays.asList("Bar_Code", "po_name"), Arrays.asList(curBarcode, POName));
					break;
					
				}
			}
		}
	}
	
	public void UpdateStorageOutQty(String outQty, String barcode, String batchLot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		((ITableInterface)hHandle).UpdateRecordByKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
	
	public int UpdateShippingRecord(List<List<String>> recordList, String proBarcode, String appPONum, int used_count)
	{
		int rtnRst = used_count;
		Shipping_Record hSRHandle = new Shipping_Record(new EarthquakeManagement());
		for (int iCol = 0; iCol < recordList.get(0).size(); iCol++)
		{
			String batchLot = recordList.get(0).get(iCol);
			int sql_in_count = Integer.parseInt(recordList.get(1).get(iCol));
			int sql_out_count = Integer.parseInt(recordList.get(2).get(iCol));
			String ordername = recordList.get(3).get(iCol);
			int recordCount = sql_in_count - sql_out_count;
			if (recordCount >= rtnRst)
			{
				UpdateStorageOutQty(Integer.toString(sql_out_count+rtnRst), proBarcode, batchLot);
				hSRHandle.AddARecord(appPONum, proBarcode, batchLot, ordername, Integer.toString(rtnRst));
				rtnRst = 0;
				break;
			}
			else
			{
				UpdateStorageOutQty(Integer.toString(sql_in_count), proBarcode, batchLot);
				hSRHandle.AddARecord(appPONum, proBarcode, batchLot, ordername, Integer.toString(recordCount));
				rtnRst -= recordCount;
			}
		}
		return rtnRst;
	}
}
