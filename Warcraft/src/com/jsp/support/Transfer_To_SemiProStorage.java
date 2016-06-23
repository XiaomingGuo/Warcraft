package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.*;

public class Transfer_To_SemiProStorage extends PageParentClass
{
	public List<String> GetSemiProductStoragePOList()
	{
		IStorageTableInterface hHandle = new Semi_Product_Storage(new EarthquakeManagement());
		((DBTableParent)hHandle).QueryRecordGroupByList(Arrays.asList("po_name"));
		return hHandle.getDBRecordList("po_name");
	}
}
