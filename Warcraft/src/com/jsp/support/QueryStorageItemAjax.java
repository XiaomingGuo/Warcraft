package com.jsp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class QueryStorageItemAjax extends PageParentClass
{
	public List<String> QueryProTypeStorage(String storageName)
	{
		DBTableParent hPTHandle = new DatabaseStore("Product_Type");
		hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storageName));
		return hPTHandle.getDBRecordList("name");
	}
	
	public List<String> QueryProNameByProType(String proType)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		return hPIHandle.getDBRecordList("name");
	}
	
	public List<String> GetAllRecordByBarCodeList(List<String> barcodeList)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcodeList.get(idx)));
			rtnRst.add(Integer.toString(idx+1));
			rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
			rtnRst.add(hPIHandle.getDBRecordList("Bar_Code").get(0));
			rtnRst.add(hPIHandle.getDBRecordList("product_type").get(0));
			DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
			int in_Qty = hOSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcodeList.get(idx), "1"));
			int out_Qty = hOSHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcodeList.get(idx), "1"));
			double perProPrice = Double.parseDouble(hPIHandle.getDBRecordList("sample_price").get(0));
			double totalPrice = hOSHandle.GetDblPriceOfStorage("OtherStorage", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Bar_Code", barcodeList.get(idx));
			rtnRst.add(Integer.toString(in_Qty));
			rtnRst.add(Integer.toString(out_Qty));
			rtnRst.add(Integer.toString(in_Qty-out_Qty));
			NumberFormat formatter = new DecimalFormat("#.####");
			rtnRst.add(formatter.format(perProPrice));
			rtnRst.add(formatter.format(totalPrice));
		}
		return rtnRst;
	}
	
	public String QueryBarCodeByProName(String proName)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proName));
		return hPIHandle.getDBRecordList("Bar_Code").get(0);
	}
	
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