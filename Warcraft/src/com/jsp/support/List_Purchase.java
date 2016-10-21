package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class List_Purchase extends PageParentClass
{
	public List<String> GetCustomerPoVendorGroup(String POName)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name"), Arrays.asList(POName), Arrays.asList("vendor"));
		rtnRst = hCPRHandle.getDBRecordList("vendor");
		return rtnRst;
	}
	
	public List<List<String>> GetCustomerPoRecordList(String vendor, String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor", "po_name"), Arrays.asList(vendor, POName));
		if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] keywordList = {"Bar_Code", "QTY", "percent"};
			for(int idx = 0; idx < keywordList.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(keywordList[idx]));
			}
		}
		return rtnRst;
	}
	
	public String GetMBMaterialPoDeliveryDate(String vendor, String POName)
	{
		String rtnRst = null;
		DBTableParent hMMPHandle = new DatabaseStore("Mb_Material_Po");
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor", "po_name"), Arrays.asList(vendor, POName));
		if (hMMPHandle.getTableInstance().RecordDBCount() > 0)
		{
			rtnRst = hMMPHandle.getDBRecordList("date_of_delivery").get(0);
		}
		return rtnRst;
	}
}
