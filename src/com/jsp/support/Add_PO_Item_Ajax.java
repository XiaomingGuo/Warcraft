package com.jsp.support;

import java.util.Arrays;
import com.DB.operation.*;

public class Add_PO_Item_Ajax extends PageParentClass
{
	public boolean IsCustomerPoClose(String poname)
	{
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByPoNameAndMoreThanStatus(poname, "0");
		if (hCPHandle.RecordDBCount() <= 0)
			return true;
		return false;
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
