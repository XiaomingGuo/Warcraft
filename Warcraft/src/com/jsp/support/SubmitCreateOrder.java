package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class SubmitCreateOrder extends PageParentClass
{
	public List<List<String>> getCustomerPORecord(String PO_Name)
	{
		String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("po_name"), Arrays.asList(PO_Name), Arrays.asList("id"));
		for (int i = 0; i < sqlKeyList.length; i++)
			rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[i]));
		return rtnRst;
	}
	
	public String GenOrderName(String OrderHeader)
	{
		String orderName = "";
		int iCount = 1;
		Product_Order hPOHandle = new Product_Order(new EarthquakeManagement());
		do
		{
			orderName = String.format("%s_%04d", OrderHeader, iCount);
			hPOHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(orderName));
			if (hPOHandle.getDBRecordList("id").size() <= 0)
				break;
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
	
	public void UpdateCustomerPoStatus(String status, String poName)
	{
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("status", "po_name"), Arrays.asList("1", poName));
	}
	
	public void CreateCustomerOrder(String appPOName, List<List<String>> recordList)
	{
		String createDate = GenYearMonthDayString();
		List<Integer> nextOrderQty = new ArrayList<Integer>();
		
		if (recordList != null)
		{
			int recordCount = 0;
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = CalcOrderQty(recordList.get(1).get(iRow), recordList.get(3).get(iRow));
				String strBarcode = recordList.get(0).get(iRow);
				String strDeliDate = recordList.get(2).get(iRow);
				int surplusOrderQty = iAllOrderQTY - GetStorageRepertory(strBarcode, Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				nextOrderQty.add(surplusOrderQty);
				if(surplusOrderQty == iAllOrderQTY)
					recordCount++;
				else if(surplusOrderQty >= 0)
					InsertProductOrderRecord(strBarcode, strDeliDate, GetStorageRepertory(strBarcode, Arrays.asList("Bar_Code"), Arrays.asList(strBarcode)), appPOName, OrderName);
				else
					InsertProductOrderRecord(strBarcode, strDeliDate, iAllOrderQTY, appPOName, OrderName);
			}
			if(recordCount != nextOrderQty.size())
			{
				InsertProductOrder(OrderName);
				OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			}
			recordCount = 0;
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				String strBarcode = recordList.get(0).get(iRow);
				String strDeliDate = recordList.get(2).get(iRow);
				if(nextOrderQty.get(iRow) > 0)
				{
					InsertProductOrderRecord(strBarcode, strDeliDate, nextOrderQty.get(iRow), appPOName, OrderName);
					recordCount++;
				}
			}
			if(recordCount > 0)
			{
				InsertProductOrder(OrderName);
			}
		}
	}
}
