package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class ShippingSummary extends PageParentClass
{
	public List<String> GetShippingNoList(String beginDate, String endDate)
	{
		DBTableParent hSNHandle = new DatabaseStore("Shipping_No");
		hSNHandle.QueryRecordByDateSpan(beginDate, endDate);;
		if (hSNHandle.getTableInstance().RecordDBCount() >= 0)
			return hSNHandle.getDBRecordList("shipping_no");
		return null;
	}
	
	public boolean IsCustomerPoRecordHasCurrentRecord(String curBarcode, String po_name)
	{
		int recordCount = 0;
		String[] storageName = {"Product_Storage", "Semi_Pro_Storage", "Material_Storage"};
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		for(String curStore : storageName)
		{
			hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(curBarcode, curStore), po_name));
			recordCount += hCPRHandle.getTableInstance().RecordDBCount();
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
			DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
			((Customer_Po_Record)hCPRHandle.getTableInstance()).AddARecord(barCode, poName, deliveryDate, qty, vendor, percent);
			return true;
		}
		return false;
	}
}
