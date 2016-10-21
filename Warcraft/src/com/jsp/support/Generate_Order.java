package com.jsp.support;

import java.util.Arrays;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class Generate_Order extends PageParentClass
{
	public String GenOrderName(String OrderHeader)
	{
		String orderName = "";
		int iCount = 1;
		DBTableParent hPOHandle = new DatabaseStore("Product_Order");
		do
		{
			orderName = String.format("%s_%04d", OrderHeader, iCount);
			hPOHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList("MB_"+orderName));
			if (hPOHandle.getDBRecordList("id").size() <= 0)
				break;
			iCount += 1;
		}while(true);
		return orderName;
	}
}