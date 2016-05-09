package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Transfer_To_SemiProStorage extends PageParentClass
{
	public List<String> GetManuStoragePOList()
	{
		Manu_Storage_Record hMSRHandle = new Manu_Storage_Record(new EarthquakeManagement());
		hMSRHandle.QueryRecordGroupByList(Arrays.asList("po_name"));
		return hMSRHandle.getDBRecordList("po_name");
	}
}
