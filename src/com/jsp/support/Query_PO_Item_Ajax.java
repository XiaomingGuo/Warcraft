package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Query_PO_Item_Ajax extends PageParentClass
{
	public String GetCustomerPoStatus(String po_name)
	{
		String rtnRst = null;
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
		if (hCPHandle.RecordDBCount() > 0)
		{
			rtnRst = hCPHandle.getDBRecordList("status").get(0);
		}
		return rtnRst;
	}
	
	public List<List<String>> GetCustomerPoRecordList(String po_name)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
		if (hCPRHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"id", "vendor", "Bar_Code", "po_name", "delivery_date", "QTY", "percent", "isEnsure", "create_date"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
}
