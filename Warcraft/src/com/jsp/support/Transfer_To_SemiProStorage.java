package com.jsp.support;

import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.*;

public class Transfer_To_SemiProStorage extends PageParentClass
{
	public List<String> GetSemiProductStoragePOList()
	{
		DBTableParent hHandle = new DatabaseStore("Semi_Product_Storage");
		hHandle.QueryRecordGroupByList(Arrays.asList("po_name"));
		return hHandle.getDBRecordList("po_name");
	}
}
