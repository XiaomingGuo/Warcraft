package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class List_Purchase extends PageParentClass
{
	public List<String> GetCustomerPoVendorGroup(String POName)
	{
		List<String> rtnRst = new ArrayList<String>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name"), Arrays.asList(POName), Arrays.asList("vendor"));
		rtnRst = hCPRHandle.getDBRecordList("vendor");
		return rtnRst;
	}
	
	public List<List<String>> GetCustomerPoRecordList(String vendor, String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor", "po_name"), Arrays.asList(vendor, POName));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] keywordList = {"Bar_Code", "QTY", "percent", "delivery_date"};
			for(int idx = 0; idx < keywordList.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(keywordList[idx]));
			}
		}
		return rtnRst;
	}
	
	public String GetCustomerPoDeliveryDate(String vendor, String POName)
	{
		String rtnRst = null;
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor", "po_name"), Arrays.asList(vendor, POName));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			rtnRst = hCPRHandle.getDBRecordList("delivery_date").get(0);
		}
		return rtnRst;
	}

	public int GetAllProductAndMaterialRepertory(String barcode)
	{
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		return hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hMSHandle.GetUsedBarcode(barcode, "product_storage")))
				+ hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "material_storage")));
	}
}
