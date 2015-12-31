package com.jsp.support;

import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class StorageReport extends PageParentClass
{
	public int GetStorageroomName(String storageName, String strBarcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(strBarcode);
		String tempBarcode = hHandle.GetUsedBarcode(strBarcode, storageName);
		return hHandle.GetIntSumOfValue("IN_QTY", "Bar_Code", tempBarcode) - hHandle.GetIntSumOfValue("OUT_QTY", "Bar_Code", tempBarcode);
	}
	
	public List<String> GetAllStorageroom()
	{
		Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
		hSNHandle.GetAllRecord();
		return hSNHandle.getDBRecordList("name");
	}
}
