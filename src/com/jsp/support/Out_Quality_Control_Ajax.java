package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Out_Quality_Control_Ajax extends PageParentClass
{
	public int GetStorageRepertory(String barcode)
	{
		return GetIN_QTYByBarCode(barcode) - GetOUT_QTYByBarCode(barcode);
	}
	
	public int GetIN_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
	
	public List<List<String>> GetStorageRecordList(String barcode, String[] keyArray)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		IStorageTableInterface hHandle = GenStorageHandle(barcode);

		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code"), Arrays.asList(barcode));
		//List<List<String>> material_info_List = new ArrayList<List<String>>();
		for(int idx=0; idx < keyArray.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
		}
		return rtnRst;
	}
	
	public List<List<String>> GetOtherRecordList(String keyVal, String[] keyArray)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Other_Record hHandle = new Other_Record(new EarthquakeManagement());

		hHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(keyVal));
		//List<List<String>> material_info_List = new ArrayList<List<String>>();
		for(int idx=0; idx < keyArray.length; idx++)
		{
			rtnRst.add(hHandle.getDBRecordList(keyArray[idx]));
		}
		return rtnRst;
	}
	
	public void UpdateStorageOutQty(String outQty, String barcode, String batchLot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		hHandle.UpdateRecordByFilterKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
	
	public int TransferMaterialToProduct(String barcode, String batchLot, String OrderName, int used_count)
	{
		int rtnRst = 0;
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		hPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
				Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
		if (hPSHandle.RecordDBCount() > 0)
		{
			int storageQTY = hPSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
					Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
			hPSHandle.UpdateRecordByFilterKeyList("IN_QTY", Integer.toString(storageQTY+used_count), Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
					Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
		}
		else
		{
			hPSHandle.AddARecord(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, Integer.toString(used_count), "0", "0", OrderName, "00000000");
		}
		return rtnRst;
	}
}
