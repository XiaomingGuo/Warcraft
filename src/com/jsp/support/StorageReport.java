package com.jsp.support;

import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.ITableInterface;

public class StorageReport extends PageParentClass
{
	public int GetStorageroomName(String storageName, String strBarcode)
	{
		ITableInterface hHandle = null;
		String tempBarcode = "";
		if(storageName.indexOf("Product") >= 0)
		{
			hHandle = new Product_Storage(new EarthquakeManagement());
			tempBarcode = ((Product_Storage)hHandle).GetUsedBarcode(strBarcode, storageName);
		}
		else if(storageName.indexOf("Material") >= 0)
		{
			hHandle = new Material_Storage(new EarthquakeManagement());
			tempBarcode = ((Material_Storage)hHandle).GetUsedBarcode(strBarcode, storageName);
		}
		else if(storageName.indexOf("Other") >= 0)
		{
			hHandle = new Other_Storage(new EarthquakeManagement());
			tempBarcode = ((Material_Storage)hHandle).GetUsedBarcode(strBarcode, storageName);
		}
		else
			return -1;
		return hHandle.GetIntSumOfValue("IN_QTY", "Bar_Code", tempBarcode) - hHandle.GetIntSumOfValue("OUT_QTY", "Bar_Code", tempBarcode);
	}
	
	public List<String> GetAllStorageroom()
	{
		Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
		hSNHandle.GetAllRecord();
		return hSNHandle.getDBRecordList("name");
	}

}
