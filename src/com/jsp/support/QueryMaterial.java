package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class QueryMaterial extends PageParentClass
{
	public QueryMaterial() { }
	
	public List<List<String>> GetManufactureStorageRecord()
	{
		List<List<String>> rtnRst = GetAllRecordWithoutEnsure(new Material_Storage(new EarthquakeManagement()));
		List<List<String>> tempList = GetAllRecordWithoutEnsure(new Product_Storage(new EarthquakeManagement()));
		for(int idx = 0; idx < rtnRst.size(); idx++)
		{
			rtnRst.get(idx).addAll(tempList.get(idx));
		}
		tempList = GetAllRecordWithoutEnsure(new Semi_Product_Storage(new EarthquakeManagement()));
		for(int idx = 0; idx < rtnRst.size(); idx++)
		{
			rtnRst.get(idx).addAll(tempList.get(idx));
		}
		return rtnRst;
	}
	
	private List<List<String>> GetAllRecordWithoutEnsure(IStorageTableInterface hTableHandle)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "po_name", "IN_QTY", "Price_Per_Unit", "Total_Price", "vendor_name", "id", "isEnsure"};

		hTableHandle.QueryRecordByFilterKeyList(Arrays.asList("isEnsure"), Arrays.asList("0"));
		for(int keywordIdx = 0; keywordIdx < sqlKeyList.length; keywordIdx++)
		{
			rtnRst.add(hTableHandle.getDBRecordList(sqlKeyList[keywordIdx]));
		}
		return rtnRst;
	}
}
