package com.jsp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;

public class QueryStorageItemAjax extends PageParentClass
{
	public List<String> QueryProTypeStorage(String storageName)
	{
		List<String> rtnRst = null;
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storageName));
		rtnRst = hPTHandle.getDBRecordList("name");
		return rtnRst;
	}
	
	public List<String> QueryProNameByProType(String proType)
	{
		List<String> rtnRst = null;
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		rtnRst = hPIHandle.getDBRecordList("name");
		return rtnRst;
	}
	
	public List<String> GetAllRecordByBarCodeList(List<String> barcodeList)
	{
		List<String> rtnRst = new ArrayList<String>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcodeList.get(idx)));
			rtnRst.add(Integer.toString(idx+1));
			rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
			rtnRst.add(hPIHandle.getDBRecordList("Bar_Code").get(0));
			rtnRst.add(hPIHandle.getDBRecordList("product_type").get(0));
			Other_Storage hOSHandle = new Other_Storage(new EarthquakeManagement());
			int in_Qty = hOSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcodeList.get(idx), "1"));
			int out_Qty = hOSHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(barcodeList.get(idx), "1"));
			double totalPrice = hOSHandle.GetDblPriceOfStorage("Bar_Code", barcodeList.get(idx));
			rtnRst.add(Integer.toString(in_Qty));
			rtnRst.add(Integer.toString(out_Qty));
			rtnRst.add(Integer.toString(in_Qty-out_Qty));
			NumberFormat formatter = new DecimalFormat("#.####");
			rtnRst.add(formatter.format(totalPrice));
		}
		return rtnRst;
	}
	
	public String QueryBarCodeByProName(String proName)
	{
		String rtnRst = "";
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(proName));
		rtnRst = hPIHandle.getDBRecordList("Bar_Code").get(0);
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
			hPOHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList("MB_"+orderName));
			if (hPOHandle.getDBRecordList("id").size() <= 0)
				break;
			iCount += 1;
		}while(true);
		return orderName;
	}
	
}