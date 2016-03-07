package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.Warcraft.Interface.IStorageTableInterface;

public class Summary extends PageParentClass
{
	public int GetIN_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
	
	public double GetRepertoryPrice(String barcode)
	{
		double rtnRst = 0.0;
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		List<String> inQtyList = hHandle.getDBRecordList("IN_QTY");
		List<String> outQtyList = hHandle.getDBRecordList("OUT_QTY");
		List<String> unitPrice = hHandle.getDBRecordList("Price_Per_Unit");
		for(int idx=0; idx < inQtyList.size(); idx++)
		{
			rtnRst += (Integer.parseInt(inQtyList.get(idx)) - Integer.parseInt(outQtyList.get(idx))) * Double.parseDouble(unitPrice.get(idx));
		}
		return rtnRst;
	}
}
