package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class SubmitManualOrder extends PageParentClass
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
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("status", "po_name"), Arrays.asList("1", poName));
	}
	
	public void SubmitPoOrder(String appPOName)
	{
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(appPOName));
		if (hCPHandle.RecordDBCount() <= 0)
			hCPHandle.AddARecord(appPOName);
	}
	
	public List<Integer> getCustomerPOTotalQty(List<List<String>> recordList)
	{
		List<Integer> rtnRst = new ArrayList<Integer>();
		for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
		{
			rtnRst.add(CalcOrderQty(recordList.get(1).get(iRow), recordList.get(3).get(iRow)));
		}
		return rtnRst;
	}
	
	public void CheckAndInsertProductOrder(String poName, String pro_order)
	{
		Product_Order_Record hPORHandle = new Product_Order_Record(new EarthquakeManagement());
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
		if(hPORHandle.RecordDBCount() > 0)
			InsertProductOrder(pro_order);
	}
	
	public List<Integer> CreateProduceOrderFromProductStorage(String appPOName, List<List<String>> recordList, List<Integer> nextOrderQty)
	{
		List<Integer> rtnRst = new ArrayList<Integer>();
		String createDate = GenYearMonthDayString();
		
		if (recordList != null)
		{
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = nextOrderQty.get(iRow);
				String strBarcode = recordList.get(0).get(iRow);
				int repertory = GetStorageRepertory(GetUsedBarcode(strBarcode, "Product_Storage"), Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				rtnRst.add(iAllOrderQTY - repertory);
				if(rtnRst.get(iRow) >= 0&&rtnRst.get(iRow) < iAllOrderQTY)
					InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), repertory, appPOName, OrderName);
				else if(rtnRst.get(iRow) < 0)
					InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), iAllOrderQTY, appPOName, OrderName);
			}
			CheckAndInsertProductOrder(appPOName, OrderName);
			PushOrderToDown(OrderName);
		}
		return rtnRst;
	}
	
	public List<Integer> CreateProduceOrderFromMaterialStorage(String appPOName, List<List<String>> recordList, List<Integer> nextOrderQty)
	{
		List<Integer> rtnRst = new ArrayList<Integer>();
		String createDate = GenYearMonthDayString();
		
		if (recordList != null)
		{
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = nextOrderQty.get(iRow);
				String strBarcode = recordList.get(0).get(iRow);
				int repertory = GetStorageRepertory(GetUsedBarcode(strBarcode, "Material_Storage"), Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
				rtnRst.add(iAllOrderQTY - repertory);
				if(rtnRst.get(iRow) >= 0&&rtnRst.get(iRow) < iAllOrderQTY)
					InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), repertory, appPOName, OrderName);
				else if(rtnRst.get(iRow) < 0)
					InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), iAllOrderQTY, appPOName, OrderName);
			}
			CheckAndInsertProductOrder(appPOName, OrderName);
		}
		return rtnRst;
	}
	
	public void CreateProduceOrderFromMaterialLack(String appPOName, List<List<String>> recordList, List<Integer> nextOrderQty)
	{
		if (recordList != null)
		{
			//{"Bar_Code", "QTY", "delivery_date", "percent"};
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				String strBarcode = recordList.get(0).get(iRow);
				InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), nextOrderQty.get(iRow), "Internal_Po", appPOName);
			}
			CheckAndInsertProductOrder("Internal_Po", appPOName);
		}
	}
	
	public void PushOrderToDown(String OrderName)
	{
		
	}
}
