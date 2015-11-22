package com.jsp.support;

import java.util.ArrayList;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.ITableInterface;

public class SubmitCreateOrder extends PageParentClass
{
	private ITableInterface GenDBHandle(String storeroom)
	{
		ITableInterface rtnRst = null;
		if (storeroom.toLowerCase().indexOf("material") >= 0)
		{
			rtnRst = new Material_Storage(new EarthquakeManagement());
		}
		else if(storeroom.toLowerCase().indexOf("product") >= 0)
		{
			rtnRst = new Product_Storage(new EarthquakeManagement());
		}
		else
		{
			rtnRst = new Other_Storage(new EarthquakeManagement());
		}
		return rtnRst;
	}

	public List<List<String>> getCustomerPORecord(String PO_Name)
	{
		String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
		List<List<String>> rtnRst = new ArrayList<List<String>>();
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
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		do
		{
			orderName = OrderHeader + "_" + Integer.toString(iCount);
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
	
	public int GetInProcessQty(String strBarcode, String appPOName)
	{
		Product_Order_Record hHandle = new Product_Order_Record(new EarthquakeManagement());
		return hHandle.GetQtyByBarcodeAndPOName(strBarcode, appPOName, "QTY");
	}
	
	public int GetRepertoryByBarCode(String strBarcode)
	{
		Product_Storage hHandle = new Product_Storage(new EarthquakeManagement());
		return hHandle.GetIntSumOfValue("IN_QTY", "Bar_Code", strBarcode) - hHandle.GetIntSumOfValue("OUT_QTY", "Bar_Code", strBarcode);
	}
	
	public void UpdateCustomerPoStatus(String status, String poName)
	{
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.UpdateStatusByPoName(1, poName);
	}
}
