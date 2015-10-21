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
	
	public void InsertProductOrder(String orderName)
	{
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		hPOHandle.AddARecord(orderName);
	}
	
	public void InsertProductOrderRecord(String barCode, String deliveryDate, int qty, String poName, String orderName)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.AddARecord(barCode, deliveryDate, qty, poName, orderName);
	}
	
}
