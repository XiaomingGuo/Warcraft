package com.jsp.support;

import java.util.Calendar;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class Submit_Material_Ajax extends PageParentClass
{
	public String GenBatchLot(String storageName, String strBarcode)
	{
		String rtnRst = "";
		Calendar mData = Calendar.getInstance();
		String batch_lot_Head = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		IStorageTableInterface hStorageHandle = GetStorageHandle(storageName);
		IStorageTableInterface hExStorageHandle = GetExhaustedStorageHandle(storageName);
		int loopNum = 1;
		do
		{
			rtnRst = batch_lot_Head + "-" + String.format("%02d", loopNum);
			hStorageHandle.QueryRecordByBarcodeAndBatchLot(strBarcode, rtnRst);
			hExStorageHandle.QueryRecordByBarcodeAndBatchLot(strBarcode, rtnRst);
			if ((hStorageHandle.RecordDBCount()+hExStorageHandle.RecordDBCount()) <= 0)
			{
				break;
			}
			loopNum ++;
		}
		while(true);
		return rtnRst;
	}
	
	private IStorageTableInterface GetExhaustedStorageHandle(String storageName)
	{
		if(storageName.toLowerCase().indexOf("product") >= 0)
		{
			return new Exhausted_Product(new EarthquakeManagement());
		}
		else if(storageName.toLowerCase().indexOf("material") >= 0)
		{
			return new Exhausted_Material(new EarthquakeManagement());
		}
		else if(storageName.toLowerCase().indexOf("other") >= 0)
		{
			return new Exhausted_Other(new EarthquakeManagement());
		}
		return null;
	}
	
	private IStorageTableInterface GetStorageHandle(String storageName)
	{
		if(storageName.toLowerCase().indexOf("product") >= 0)
		{
			return new Product_Storage(new EarthquakeManagement());
		}
		else if(storageName.toLowerCase().indexOf("material") >= 0)
		{
			return new Material_Storage(new EarthquakeManagement());
		}
		else if(storageName.toLowerCase().indexOf("other") >= 0)
		{
			return new Other_Storage(new EarthquakeManagement());
		}
		return null;
	}
	
	public void AddARecordToStorage(String storageName, String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate)
	{
		IStorageTableInterface hStorageHandle = GetStorageHandle(storageName);
		hStorageHandle.AddARecord(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, appSupplier_name, appInStoreDate);
	}
}
