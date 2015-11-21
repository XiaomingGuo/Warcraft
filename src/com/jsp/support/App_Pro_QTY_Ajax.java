package com.jsp.support;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class App_Pro_QTY_Ajax extends PageParentClass
{
	private ITableInterface GenDBHandle(String storeroom)
	{
		ITableInterface rtnRst = null;
		if (storeroom.toLowerCase().indexOf("material") >= 0)
		{
			rtnRst = new Material_Storage(new EarthquakeManagement());
		}
		else if(storeroom.toLowerCase().indexOf("product") >= 0)
		{
			rtnRst = new Product_Storage(new EarthquakeManagement());
		}
		else
		{
			rtnRst = new Other_Storage(new EarthquakeManagement());
		}
		return rtnRst;
	}
	
	public int GetIN_QTYByBarCode(String barcode)
	{
		String storeroom = GetStorageNameByBarCode(barcode, false);
		ITableInterface hHandle = GenDBHandle(storeroom);
		return hHandle.GetIntSumOfValue(storeroom, "IN_QTY", "Bar_Code", barcode);
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		String storeroom = GetStorageNameByBarCode(barcode, false);
		ITableInterface hHandle = GenDBHandle(storeroom);
		return hHandle.GetIntSumOfValue(storeroom, "OUT_QTY", "Bar_Code", barcode);
	}
}
