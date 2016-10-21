package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class Generate_MB_PO_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String appPOName, String appVendor)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "vendor"), Arrays.asList(appPOName, appVendor));
		if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] colNames = {"Bar_Code", "vendor", "QTY", "percent"};
			for(int idx=0; idx < colNames.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(colNames[idx]));
			}
		}
		return rtnRst;
	}
}
