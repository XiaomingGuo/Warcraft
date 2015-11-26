package com.jsp.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.ITableInterface;

public class SubmitCreateOrder extends PageParentClass
{
	public List<List<String>> getCustomerPORecord(String PO_Name)
	{
		String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		hCPRHandle.GetRecordByPoName(PO_Name);
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
			hPOHandle.GetRecordByOrderName(orderName);
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
	
	public void CreateCustomerOrder(String appPOName, List<List<String>> recordList)
	{
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
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
				int surplusOrderQty = iAllOrderQTY - GetRepertoryByBarCode(strBarcode);
				nextOrderQty.add(surplusOrderQty);
				if(surplusOrderQty == iAllOrderQTY)
					recordCount++;
				else if(surplusOrderQty >= 0)
					InsertProductOrderRecord(strBarcode, strDeliDate, GetRepertoryByBarCode(strBarcode), appPOName, OrderName);
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
