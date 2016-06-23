package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Generate_MB_PO_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String appPOName, String appVendor)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "vendor"), Arrays.asList(appPOName, appVendor));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] colNames = {"Bar_Code", "vendor", "QTY", "percent"};
			for(int idx=0; idx < colNames.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(colNames[idx]));
			}
		}
		return rtnRst;
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
}
