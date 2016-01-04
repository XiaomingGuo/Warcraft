package com.jsp.support;

import java.util.List;

import com.DB.operation.*;

public class MonthReport extends PageParentClass
{
	public List<String> GetAllStorageroom()
	{
		Storeroom_Name hSNHandle = new Storeroom_Name(new EarthquakeManagement());
		hSNHandle.GetAllRecord();
		return hSNHandle.getDBRecordList("name");
	}
}
