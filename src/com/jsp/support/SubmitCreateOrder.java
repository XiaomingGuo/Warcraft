package com.jsp.support;

import java.util.List;

import com.DB.operation.*;

public class SubmitCreateOrder
{
	public List<List<String>> getCustomerPORecord(String PO_Name, String[] sqlKeyList)
	{
		List<List<String>> rtnRst = null;
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.GetRecordByPoName(PO_Name);
		for (int i = 0; i < sqlKeyList.length; i++)
		{
			rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[i]));
		}
		return rtnRst;
	}
	
	public String GenOrderName(String OrderHeader)
	{
		String orderName = "";
		int iCount = 1;
		do
		{
			orderName = OrderHeader + "_" + Integer.toString(iCount);
			Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
			hPOHandle.GetRecordByOrderName(orderName);
			if (hPOHandle.getDBRecordList("id").size() <= 0)
			{
				break;
			}
			iCount += 1;
		}while(true);
		return orderName;
	}
	
	public void InsertProductOrderRecord(String orderName)
	{
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		//hPOHandle.AddRecord();
		//sql = "INSERT INTO product_order (Order_Name) VALUES ('" + OrderName + "')";
		//hDBHandle.execUpate(sql);
	}
}
