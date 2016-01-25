package com.jsp.support;

import java.util.Arrays;

import com.Warcraft.Interface.*;

public class App_Pro_QTY_Ajax extends PageParentClass
{
	public int GetIN_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcode, "1"));
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcode, "1"));
	}
}
