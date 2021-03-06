package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class Get_ProductNameType_By_Order_Ajax extends PageParentClass
{
	public List<List<String>> GetProNameByPoName(String poName, String storageName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hHandle = GenStorageHandleByStorageName(storageName);
		hHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name"), Arrays.asList(poName), Arrays.asList("Bar_Code"));
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		
		List<String> nameList = new ArrayList<String>();
		List<String> proTypeList = new ArrayList<String>();
		for(int idx=0; idx < hHandle.getTableInstance().RecordDBCount(); idx++)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hHandle.getDBRecordList("Bar_Code").get(idx)));
			nameList.add(hPIHandle.getDBRecordList("name").get(0));
			proTypeList.add(hPIHandle.getDBRecordList("product_type").get(0));
		}
		rtnRst.add(nameList);
		rtnRst.add(proTypeList);
		return rtnRst;
	}
}
