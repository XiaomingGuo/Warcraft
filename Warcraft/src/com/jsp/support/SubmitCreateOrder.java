package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class SubmitCreateOrder extends PageParentClass
{
	public List<List<String>> getCustomerPORecord(String PO_Name)
	{
		String[] sqlKeyList = {"Bar_Code", "QTY", "delivery_date", "percent"};
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("po_name"), Arrays.asList(PO_Name), Arrays.asList("id"));
		for (int i = 0; i < sqlKeyList.length; i++)
			rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[i]));
		return rtnRst;
	}
	
	public String GenOrderName(String OrderHeader)
	{
		String orderName = "";
		int iCount = 1;
		DBTableParent hPOHandle = new DatabaseStore("Product_Order");
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
		DBTableParent hPOHandle = new DatabaseStore("Product_Order");
		((Product_Order)hPOHandle.getTableInstance()).AddARecord(orderName);
	}
	
	public void InsertProductOrderRecord(String barCode, String deliveryDate, int qty, String poName, String orderName)
	{
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		((Product_Order_Record)hPORHandle.getTableInstance()).AddARecord(barCode, deliveryDate, qty, poName, orderName);
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
