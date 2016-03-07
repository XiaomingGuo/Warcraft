package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class MonthReport extends PageParentClass
{
	public int GetStorageroomName(String storageName, String strBarcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(strBarcode);
		String tempBarcode = hHandle.GetUsedBarcode(strBarcode, storageName);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code"), Arrays.asList(tempBarcode)) - 
				hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(tempBarcode));
	}
	
	public List<String> GetUserName(List<String> groupList)
	{
		Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
		hORHandle.QueryRecordGroupByList(groupList);
		return hORHandle.getDBRecordList("user_name");
	}
}
