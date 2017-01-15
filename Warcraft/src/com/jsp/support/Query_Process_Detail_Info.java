package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class Query_Process_Detail_Info extends PageParentClass
{
	public void AddProductTypeToDatabase(String proType, String storeName)
	{
		DBTableParent hPTHandle = new DatabaseStore("Product_Type");
		if(storeName.indexOf("原材料库") == 0)
		{
			((Product_Type)hPTHandle.getTableInstance()).AddARecord(proType, "成品库");
			((Product_Type)hPTHandle.getTableInstance()).AddARecord(proType + "半成品", "半成品库");
			((Product_Type)hPTHandle.getTableInstance()).AddARecord(proType + "原锭", storeName);
		}
		else
			((Product_Type)hPTHandle.getTableInstance()).AddARecord(proType, storeName);
	}
	
	public List<String> GetProductInfoByBarcode(String barcode)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		String[] keyWordList = new String[]{"id", "Bar_Code", "name", "product_type", "weight", "sample_price", "sample_vendor", "process_name", "capacity", "description"};
		for(int idx = 0; idx < keyWordList.length; idx++)
			rtnRst.add(hPIHandle.getDBRecordList(keyWordList[idx]).get(0));
		return rtnRst;
	}
	
	public List<List<String>> GetProductInfoByProType(String proType, List<String> getKeyList)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		for(int idx=0; idx < getKeyList.size(); idx++)
			rtnRst.add(hPIHandle.getDBRecordList(getKeyList.get(idx)));
		return rtnRst;
	}
	
	public List<List<String>> GetProductInfoByProNameAndProType(String proName, String proType)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(proName, proType));
		String[] keyword = new String[] {"Bar_Code", "weight", "description", "sample_price", "sample_vendor"};
		
		for(int idx=0; idx < keyword.length; idx++)
			rtnRst.add(hPIHandle.getDBRecordList(keyword[idx]));
		return rtnRst;
	}
	
	public String GetWeightByBarcode(String barcode, String storageName)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPIHandle.GetUsedBarcode(barcode, storageName)));
		return hPIHandle.getDBRecordList("weight").get(0);
	}
	
	public List<String> GetProNameAndTypeAndStorageName(String barcode)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
		if(hPIHandle.getTableInstance().RecordDBCount() > 0)
		{
			String proType = hPIHandle.getDBRecordList("product_type").get(0);
			String proName = hPIHandle.getDBRecordList("name").get(0);
			
			DBTableParent hPTHandle = new DatabaseStore("Product_Type");
			hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proType));
			String storeroom = hPTHandle.getDBRecordList("storeroom").get(0);
			rtnRst.add(storeroom);
			rtnRst.add(proType);
			rtnRst.add(proName);
		}
		return rtnRst;
	}
}
