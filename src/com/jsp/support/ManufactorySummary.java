package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class ManufactorySummary extends PageParentClass
{
	public List<String> GetMFGStoreName()
	{
		List<String> rtnRst = new ArrayList<String>();
		List<String> displayStoreName = Arrays.asList("成品库", "原材料库", "半成品库");
		Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
		hSNHandle.GetAllRecord();
		List<String> tempList = hSNHandle.getDBRecordList("name");
		
		for(String storeName : tempList)
		{
			if(!displayStoreName.contains(storeName))
				continue;
			rtnRst.add(storeName);
		}
		return rtnRst;
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
}
