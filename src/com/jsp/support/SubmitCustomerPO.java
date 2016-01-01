package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class SubmitCustomerPO extends PageParentClass
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
	
	public int GetRepertoryByBarCode(String storageName, String strBarcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(strBarcode);;
		String tempBarcode = hHandle.GetUsedBarcode(strBarcode, storageName);
		return hHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code"), Arrays.asList(tempBarcode)) - 
				hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(tempBarcode));
	}
	
	public void UpdateCustomerPoStatus(String status, String poName)
	{
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.UpdateStatusByPoName(1, poName);
	}
	
	public void SubmitPoOrder(String appPOName)
	{
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		hCPHandle.GetRecordByPoName(appPOName);
		if (hCPHandle.getDBRecordList("id").size() <= 0)
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
		Product_Order_Record hHandle = new Product_Order_Record(new EarthquakeManagement());
		hHandle.GetRecordByOrderName(pro_order);
		if(hHandle.RecordDBCount() > 0)
			InsertProductOrder(pro_order);
	}
	
	public List<Integer> CreateProduceOrderFromProductStorage(String appPOName, List<List<String>> recordList, List<Integer> nextOrderQty)
	{
		List<Integer> rtnRst = new ArrayList<Integer>();
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		
		if (recordList != null)
		{
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = nextOrderQty.get(iRow);
				String strBarcode = recordList.get(0).get(iRow);
				int repertory = GetRepertoryByBarCode("Product_Storage", strBarcode);
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
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		
		if (recordList != null)
		{
			String OrderName = GenOrderName("MB_" + createDate + "_" + appPOName);
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iAllOrderQTY = nextOrderQty.get(iRow);
				String strBarcode = recordList.get(0).get(iRow);
				int repertory = GetRepertoryByBarCode("Material_Storage", strBarcode);
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
		Calendar mData = Calendar.getInstance();
		String createDate = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		
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
		Product_Order_Record hHandle = new Product_Order_Record(new EarthquakeManagement());
		hHandle.GetRecordByOrderName(OrderName);
		List<String> orderBarcodeList = hHandle.getDBRecordList("Bar_Code");
		List<String> orderQTY = hHandle.getDBRecordList("QTY");
		for (int idx = 0; idx < orderBarcodeList.size(); idx++)
		{
			hHandle.UpdatetRecordByOrderName(OrderName, orderBarcodeList.get(idx), "status", "5");
			hHandle.UpdatetRecordByOrderName(OrderName, orderBarcodeList.get(idx), "completeQty", orderQTY.get(idx));
			hHandle.UpdatetRecordByOrderName(OrderName, orderBarcodeList.get(idx), "oqcQty", orderQTY.get(idx));
		}
	}
	
	private void UpdateProductOrderStatusToFinish(String OrderName)
	{
		Product_Order hHandle = new Product_Order(new EarthquakeManagement());
		hHandle.UpdateRecordByKeyList("status", "1", Arrays.asList("order_name"), Arrays.asList(OrderName));
	}
}
