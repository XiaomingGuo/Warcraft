package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class Query_Shipping_No_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetShippingRecordByShippingNo(String shipNo)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hSRHandle = new DatabaseStore("Shipping_Record");
		hSRHandle.QueryRecordByFilterKeyList(Arrays.asList("shipping_no"), Arrays.asList(shipNo));
		if (hSRHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"id", "customer_po", "Bar_Code", "Batch_Lot", "Order_Name", "ship_QTY"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hSRHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	public String GetShippingDate(String shipNo)
	{
		String rtnRst = "";
		DBTableParent hSNHandle = new DatabaseStore("Shipping_No");
		hSNHandle.QueryRecordByFilterKeyList(Arrays.asList("shipping_no"), Arrays.asList(shipNo));
		if (hSNHandle.getTableInstance().RecordDBCount() > 0)
		{
			rtnRst = hSNHandle.getDBRecordList("create_date").get(0);
		}
		return rtnRst;
	}
}
