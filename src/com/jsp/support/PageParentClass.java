package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.EarthquakeManagement;
import com.DB.operation.Exhausted_Material;
import com.DB.operation.Exhausted_Other;
import com.DB.operation.Exhausted_Product;
import com.DB.operation.Material_Storage;
import com.DB.operation.Other_Storage;
import com.DB.operation.Product_Storage;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.Interface.ITableInterface;

public class PageParentClass
{
	public int CalcOrderQty(String po_Num, String percent)
	{
		return Integer.parseInt(po_Num) * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(int po_Num, String percent)
	{
		return po_Num * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(String po_Num, int percent)
	{
		return Integer.parseInt(po_Num) * (100 + percent)/100;
	}
	
	public int CalcOrderQty(int po_Num, int percent)
	{
		return po_Num * (100 + percent)/100;
	}
	
	public String GetStorageNameByBarCode(String Bar_Code, boolean isExhausted)
	{
		String rtnRst = "";
		int barcode = Integer.parseInt(Bar_Code);
		if(barcode >= 50000000 && barcode < 60000000) {
			rtnRst = isExhausted?"ExhaustedMaterial":"MaterialStorage";
		}
		else if(barcode >= 60000000 && barcode < 70000000) {
			rtnRst = isExhausted?"ExhaustedProduct":"ProductStorage";
		}
		else if(barcode >= 70000000 && barcode < 80000000) {
			rtnRst = isExhausted?"":"";
		}
		else {
			rtnRst = isExhausted?"ExhaustedOther":"OtherStorage";
		}
		return rtnRst;
	}

	public IStorageTableInterface GenStorageHandle(String barcode)
	{
		int iBarcode = Integer.parseInt(barcode);
		if(iBarcode < 50000000||iBarcode >= 80000000)
			return new Other_Storage(new EarthquakeManagement());
		else if(iBarcode >= 50000000&&iBarcode < 60000000)
			return new Material_Storage(new EarthquakeManagement());
		else if(iBarcode >= 60000000&&iBarcode < 70000000)
			return new Product_Storage(new EarthquakeManagement());
		return null;	
	}
	
	public IStorageTableInterface GenExStorageHandle(String barcode)
	{
		int iBarcode = Integer.parseInt(barcode);
		if(iBarcode < 50000000||iBarcode >= 80000000)
			return new Exhausted_Other(new EarthquakeManagement());
		else if(iBarcode >= 50000000&&iBarcode < 60000000)
			return new Exhausted_Material(new EarthquakeManagement());
		else if(iBarcode >= 60000000&&iBarcode < 70000000)
			return new Exhausted_Product(new EarthquakeManagement());
		return null;	
	}
	
	public void CheckMoveToExhaustedTable(String barcode, String batchLot)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
		hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
		if (hStorageHandle.getDBRecordList("IN_QTY").equals(hStorageHandle.getDBRecordList("OUT_QTY")))
		{
			String diffKeyWord = "";
			if (GetStorageNameByBarCode(barcode, true).contains("Product"))
				diffKeyWord = "Order_Name";
			else
				diffKeyWord = "vendor_name";
			IStorageTableInterface hExStorageHandle = GenExStorageHandle(barcode);
			hExStorageHandle.AddAExRecord(hStorageHandle.getDBRecordList("id").get(0), hStorageHandle.getDBRecordList("Bar_Code").get(0), 
					hStorageHandle.getDBRecordList("Batch_Lot").get(0), hStorageHandle.getDBRecordList("IN_QTY").get(0),
					hStorageHandle.getDBRecordList("OUT_QTY").get(0), hStorageHandle.getDBRecordList("Price_Per_Unit").get(0),
					hStorageHandle.getDBRecordList("Total_Price").get(0), hStorageHandle.getDBRecordList(diffKeyWord).get(0),
					hStorageHandle.getDBRecordList("in_store_date").get(0), hStorageHandle.getDBRecordList("isEnsure").get(0),
					hStorageHandle.getDBRecordList("create_date").get(0));
			((ITableInterface)hStorageHandle).DeleteRecordByKeyWord("Id", Arrays.asList(hStorageHandle.getDBRecordList("id").get(0)));
		}
	}
}
