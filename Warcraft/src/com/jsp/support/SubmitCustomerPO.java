package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class SubmitCustomerPO extends PageParentClass
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
	
	public void SubmitPoOrder(String appPOName)
	{
		DBTableParent hCPHandle = new DatabaseStore("Customer_Po");
		hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(appPOName));
		if (hCPHandle.getTableInstance().RecordDBCount() <= 0)
			((Customer_Po)hCPHandle.getTableInstance()).AddARecord(appPOName);
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
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(pro_order));
		if(hPORHandle.getTableInstance().RecordDBCount() > 0)
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
				if(iAllOrderQTY > 0)
				{
					if(rtnRst.get(iRow) >= 0&&rtnRst.get(iRow) < iAllOrderQTY)
						InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), repertory, appPOName, OrderName);
					else if(rtnRst.get(iRow) < 0)
						InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), iAllOrderQTY, appPOName, OrderName);
				}
			}
			CheckAndInsertProductOrder(appPOName, OrderName);
		}
		return rtnRst;
	}
	
	public void CreateProduceOrderFromMaterialLack(String appPOName, List<List<String>> recordList, List<Integer> nextOrderQty)
	{
		String createDate = GenYearMonthDayString();
		
		if (recordList != null)
		{
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = nextOrderQty.get(iRow);
				if(iAllOrderQTY > 0)
				{
					String strBarcode = recordList.get(0).get(iRow);
					InsertProductOrderRecord(strBarcode, recordList.get(2).get(iRow), iAllOrderQTY, appPOName, OrderName);
				}
			}
			CheckAndInsertProductOrder(appPOName, OrderName);
		}
	}
	
	public void PushOrderToDown(String OrderName)
	{
		UpdateProductOrderRecordStatusToFinish(OrderName);
		UpdateProductOrderStatusToFinish(OrderName);
	}

	private void UpdateProductOrderRecordStatusToFinish(String OrderName)
	{
		DBTableParent hPORHandle = new DatabaseStore("Product_Order_Record");
		hPORHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList(OrderName));
		List<String> orderBarcodeList = hPORHandle.getDBRecordList("Bar_Code");
		List<String> orderQTY = hPORHandle.getDBRecordList("QTY");
		for (int idx = 0; idx < orderBarcodeList.size(); idx++)
		{
			hPORHandle.UpdateRecordByKeyList("status", "5", Arrays.asList("Order_Name", "Bar_Code"), Arrays.asList(OrderName, orderBarcodeList.get(idx)));
			hPORHandle.UpdateRecordByKeyList("completeQty", orderQTY.get(idx), Arrays.asList("Order_Name", "Bar_Code"), Arrays.asList(OrderName, orderBarcodeList.get(idx)));
			hPORHandle.UpdateRecordByKeyList("oqcQty", orderQTY.get(idx), Arrays.asList("Order_Name", "Bar_Code"), Arrays.asList(OrderName, orderBarcodeList.get(idx)));
		}
	}
	
	private void UpdateProductOrderStatusToFinish(String OrderName)
	{
		DBTableParent hPOHandle = new DatabaseStore("Product_Order");
		hPOHandle.UpdateRecordByKeyList("status", "1", Arrays.asList("order_name"), Arrays.asList(OrderName));
	}
}
