package com.jsp.support;

import com.Warcraft.Interface.*;

public class App_Pro_QTY_Ajax extends PageParentClass
{
	public int GetIN_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("IN_QTY", "Bar_Code", barcode);
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", "Bar_Code", barcode);
	}
}
