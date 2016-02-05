package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.dialect.function.VarArgsSQLFunction;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Update_Customer_OUT_QTY_Ajax extends PageParentClass
{
	public List<List<String>> GetStorageRecordList(String barcode, String POName)
	{
		List<List<String>> rtnRst = _GetStorageRecordList(barcode, POName);
		List<List<String>> tempList = _GetStorageRecordList(barcode, "Material_Supply");
		if(rtnRst.size() == tempList.size())
		{
			for(int idx=0; idx < rtnRst.size(); idx++)
				rtnRst.get(idx).addAll(tempList.get(idx));
		}
		return rtnRst;
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
				String writeQTY = Integer.toString(Integer.parseInt(hCPRHandle.getDBRecordList("OUT_QTY").get(0)) + used_count);
				hCPRHandle.UpdateRecordByKeyList("OUT_QTY", writeQTY, Arrays.asList("Bar_Code", "po_name"), Arrays.asList(curBarcode, POName));
				break;
			}
		}
	}
	
	public void UpdateStorageOutQty(String outQty, String barcode, String batchLot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		((ITableInterface)hHandle).UpdateRecordByKeyList("OUT_QTY", outQty, Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		CheckMoveToExhaustedTable(barcode, batchLot);
	}
}
