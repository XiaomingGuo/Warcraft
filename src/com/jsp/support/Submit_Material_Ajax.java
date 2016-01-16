package com.jsp.support;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class Submit_Material_Ajax extends PageParentClass
{
	public String GenBatchLot(String storageName, String strBarcode)
	{
		String rtnRst = "";
		Calendar mData = Calendar.getInstance();
		String batch_lot_Head = String.format("%04d", mData.get(Calendar.YEAR)) + String.format("%02d", mData.get(Calendar.MONDAY)+1)+ String.format("%02d", mData.get(Calendar.DAY_OF_MONTH));
		IStorageTableInterface hStorageHandle = GenStorageHandle(strBarcode);
		IStorageTableInterface hExStorageHandle = GenExStorageHandle(strBarcode);
		int loopNum = 1;
		do
		{
			rtnRst = batch_lot_Head + "-" + String.format("%02d", loopNum);
			List<String> keyList = Arrays.asList("Bar_code", "Batch_Lot"), valueList = Arrays.asList(strBarcode, rtnRst);
			hStorageHandle.QueryRecordByFilterKeyList(keyList, valueList);
			hExStorageHandle.QueryRecordByFilterKeyList(keyList, valueList);
			if ((hStorageHandle.RecordDBCount()+hExStorageHandle.RecordDBCount()) <= 0)
			{
				break;
			}
			loopNum ++;
		}
		while(true);
		return rtnRst;
	}
	
	public void AddARecordToStorage(String storageName, String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(appBarcode);
		hStorageHandle.AddARecord(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, "Material_Supply", appSupplier_name, appInStoreDate);
	}
}
