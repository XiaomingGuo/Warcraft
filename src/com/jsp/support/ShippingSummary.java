package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class ShippingSummary extends PageParentClass
{
	public List<String> GetShippingNoList(String beginDate, String endDate)
	{
		List<String> rtnRst = null;
		Shipping_No hSNHandle = new Shipping_No(new EarthquakeManagement());
		hSNHandle.QueryRecordByDateSpan(beginDate, endDate);;
		if (hSNHandle.RecordDBCount() >= 0)
			rtnRst = hSNHandle.getDBRecordList("shipping_no");
		return rtnRst;
	}
	
	public boolean IsCustomerPoRecordHasCurrentRecord(String curBarcode, String po_name)
	{
		int recordCount = 0;
		String[] storageName = {"Product_Storage", "Semi_Pro_Storage", "Material_Storage"};
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		for(String curStore : storageName)
		{
			hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(curBarcode, curStore), po_name));
			recordCount += hCPRHandle.RecordDBCount();
		}
		if (recordCount <= 0)
			return true;
		return false;
	}
	
	public boolean NewARecordInCustomerPoRecord(String barCode, String poName, String deliveryDate, String qty, String vendor, String percent)
	{
		if (CheckParamValidityEqualsLength(barCode, 8)&&CheckParamValidityMoreThanLength(poName, 6)&&CheckParamValidityEqualsLength(deliveryDate, 8)&&
				CheckParamValidityMoreThanValue(qty, 0)&&CheckParamValidityEqualsLength(vendor, 1)&&CheckParamValidityMoreThanLength(percent, 0))
		{
			Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
			hCPRHandle.AddARecord(barCode, poName, deliveryDate, qty, vendor, percent);
			return true;
		}
		return false;
	}
	
}
