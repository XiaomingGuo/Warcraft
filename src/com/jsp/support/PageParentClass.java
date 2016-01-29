package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.Interface.ITableInterface;

public class PageParentClass
{
	public boolean IsOtherStorage(String barcode)
	{
		if (Integer.parseInt(barcode) < 50000000 || Integer.parseInt(barcode) >= 80000000)
			return true;
		return false;
	}
	
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
			rtnRst = isExhausted?"ExhaustedSemiProduct":"SemiProductStorage";
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
		else if(iBarcode >= 70000000&&iBarcode < 80000000)
			return new Semi_Product_Storage(new EarthquakeManagement());
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
		else if(iBarcode >= 70000000&&iBarcode < 80000000)
			return new Exhausted_Semi_Product(new EarthquakeManagement());
		return null;	
	}
	
	public void CheckMoveToExhaustedTable(String barcode, String batchLot)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
		hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
		if (hStorageHandle.getDBRecordList("IN_QTY").equals(hStorageHandle.getDBRecordList("OUT_QTY")))
		{
			String orderKeyWord = "Order_Name", PoKeyWord = "po_name";
			if (GetStorageNameByBarCode(barcode, true).contains("Other"))
			{
				orderKeyWord = "vendor_name";
				PoKeyWord = "vendor_name";
			}
			IStorageTableInterface hExStorageHandle = GenExStorageHandle(barcode);
			hExStorageHandle.AddAExRecord(hStorageHandle.getDBRecordList("id").get(0), hStorageHandle.getDBRecordList("Bar_Code").get(0), 
					hStorageHandle.getDBRecordList("Batch_Lot").get(0), hStorageHandle.getDBRecordList("IN_QTY").get(0),
					hStorageHandle.getDBRecordList("OUT_QTY").get(0), hStorageHandle.getDBRecordList("Price_Per_Unit").get(0),
					hStorageHandle.getDBRecordList("Total_Price").get(0), hStorageHandle.getDBRecordList(orderKeyWord).get(0),
					hStorageHandle.getDBRecordList(PoKeyWord).get(0), hStorageHandle.getDBRecordList("in_store_date").get(0),
					hStorageHandle.getDBRecordList("isEnsure").get(0), hStorageHandle.getDBRecordList("create_date").get(0));
			((ITableInterface)hStorageHandle).DeleteRecordByKeyWord("Id", Arrays.asList(hStorageHandle.getDBRecordList("id").get(0)));
		}
	}
	
	public double GetPrice_Pre_Unit(String bar_code, String Batch_Lot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(bar_code);
		IStorageTableInterface hExHandle = GenExStorageHandle(bar_code);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
		hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
		List<String> tempList = new ArrayList<String>();
		tempList.addAll(hHandle.getDBRecordList("Price_Per_Unit"));
		tempList.addAll(hExHandle.getDBRecordList("Price_Per_Unit"));
		return Double.parseDouble(tempList.get(0));
	}
	
	public int GetAllRepertory(String barcode, String po_name)
	{
		int rtnRst = 0;
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		rtnRst += hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hPSHandle.GetUsedBarcode(barcode, "Product_Storage"), po_name));
		rtnRst += hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hSPSHandle.GetUsedBarcode(barcode, "Semi_Pro_Storage"), po_name));
		rtnRst += hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hMSHandle.GetUsedBarcode(barcode, "Material_Storage"), po_name));
		return rtnRst;
	}
	
	public String GenBatchLot(String strBarcode)
	{
		Calendar mData = Calendar.getInstance();
		String batch_lot_Head = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		return CheckBatchLot(batch_lot_Head, strBarcode);
	}
	
	public String CheckBatchLot(String batch_lot_Head, String strBarcode)
	{
		String rtnRst = "";
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		Other_Storage hOSHandle = new Other_Storage(new EarthquakeManagement());
		Exhausted_Product hEPHandle = new Exhausted_Product(new EarthquakeManagement());
		Exhausted_Semi_Product hESPHandle = new Exhausted_Semi_Product(new EarthquakeManagement());
		Exhausted_Material hEMHandle = new Exhausted_Material(new EarthquakeManagement());
		Exhausted_Other hEOHandle = new Exhausted_Other(new EarthquakeManagement());
		int loopNum = 1;
		do
		{
			rtnRst = batch_lot_Head + "-" + String.format("%02d", loopNum);
			List<String> keyList = Arrays.asList("Bar_code", "Batch_Lot"), valueList = Arrays.asList(strBarcode, rtnRst);
			hPSHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hSPSHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hMSHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hOSHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hEPHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hESPHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hEMHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hEOHandle.QueryRecordByFilterKeyList(keyList, valueList);
			if ((hPSHandle.RecordDBCount()+hSPSHandle.RecordDBCount()+hMSHandle.RecordDBCount()+hOSHandle.RecordDBCount()+
					hEPHandle.RecordDBCount()+hESPHandle.RecordDBCount()+hEMHandle.RecordDBCount()+hEOHandle.RecordDBCount()) <= 0)
			{
				break;
			}
			loopNum ++;
		}while(true);
		return rtnRst;
	}
}
