package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class Query_Process_Detail_Info extends PageParentClass
{
	public boolean IsProductTypeExist(String proType)
	{
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proType));
		if(hPTHandle.RecordDBCount() > 0)
			return true;
		return false;
	}
	
	public boolean IsProductInfoExist(String barcode)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		if (hPIHandle.RecordDBCount() > 0)
			return true;
		return false;
	}
	
	public void AddProductTypeToDatabase(String proType, String storeName)
	{
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		if(storeName.indexOf("原材料库") == 0)
		{
			hPTHandle.AddARecord(proType, "成品库");
			hPTHandle.AddARecord(proType + "半成品", "半成品库");
			hPTHandle.AddARecord(proType + "原锭", storeName);
		}
		else
			hPTHandle.AddARecord(proType, storeName);
	}
	
	public List<String> GetProductInfoByProType(String proType)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		return hPIHandle.getDBRecordList("name");
	}
	
	public List<List<String>> GetProductInfoByProNameAndProType(String proName, String proType)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(proName, proType));
		String[] keyword = new String[] {"Bar_Code", "weight", "description", "sample_price", "sample_vendor"};
		
		for(int idx=0; idx < keyword.length; idx++)
			rtnRst.add(hPIHandle.getDBRecordList(keyword[idx]));
		return rtnRst;
	}
	
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
	
	public String GetWeightByBarcode(String barcode, String storageName)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPIHandle.GetUsedBarcode(barcode, storageName)));
		return hPIHandle.getDBRecordList("weight").get(0);
	}
	
	public List<String> GetProNameAndTypeAndStorageName(String barcode)
	{
		List<String> rtnRst = new ArrayList<String>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		if(hPIHandle.RecordDBCount() > 0)
		{
			String proType = hPIHandle.getDBRecordList("product_type").get(0);
			String proName = hPIHandle.getDBRecordList("name").get(0);
			
			Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
			hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proType));
			String storeroom = hPTHandle.getDBRecordList("storeroom").get(0);
			rtnRst.add(storeroom);
			rtnRst.add(proType);
			rtnRst.add(proName);
		}
		return rtnRst;
	}
}
