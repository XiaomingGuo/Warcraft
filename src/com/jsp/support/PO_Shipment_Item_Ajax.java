package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class PO_Shipment_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordOrderByIdASC(POName);
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "po_name", "delivery_date", "QTY", "OUT_QTY", "percent"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	public List<String> GetProductInfoList(String barcode)
	{
		List<String> rtnRst = new ArrayList<String>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		rtnRst.add(hPIHandle.getDBRecordList("product_type").get(0));
		rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
		return rtnRst;
	}
	
	public int GetProductRepertory(String barcode)
	{
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		return hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "Product_Storage")));
	}
}
