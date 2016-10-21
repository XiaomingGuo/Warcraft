package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class Out_Quality_Control_Ajax extends PageParentClass
{
	public List<List<String>> GetStorageRecordList(String barcode, String[] keyArray)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandle(barcode);
		
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
		DBTableParent hHandle = new DatabaseStore("Other_Record");
		
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
		DBTableParent hHandle = GenStorageHandle(barcode);
		hHandle.UpdateRecordByKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
	
	public int TransferMaterialToProduct(String barcode, String batchLot, String OrderName, int used_count)
	{
		int rtnRst = 0;
		DBTableParent hPSHandle = new DatabaseStore("Product_Storage");
		hPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
				Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
		if (hPSHandle.getTableInstance().RecordDBCount() > 0)
		{
			int storageQTY = hPSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
					Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
			hPSHandle.UpdateRecordByKeyList("IN_QTY", Integer.toString(storageQTY+used_count), Arrays.asList("Bar_Code", "Batch_Lot", "Order_Name"), 
					Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, OrderName));
		}
		else
		{
			((Product_Storage)hPSHandle.getTableInstance()).AddARecord(hPSHandle.GetUsedBarcode(barcode, "product_storage"), batchLot, Integer.toString(used_count), "0", "0", OrderName, " ", "MBond", "00000000");
		}
		return rtnRst;
	}
}
