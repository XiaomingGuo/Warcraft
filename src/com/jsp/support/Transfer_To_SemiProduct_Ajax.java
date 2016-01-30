package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Transfer_To_SemiProduct_Ajax extends PageParentClass
{
	public String ExecuteTransferMaterialToSemiProduct(String barcode, String poName, String putQty)
	{
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		hMSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, poName, "1"));
		
		for(int iRecordIdx = 0; iRecordIdx < hMSHandle.RecordDBCount(); iRecordIdx++)
		{
			String batchLot = hMSHandle.getDBRecordList("Batch_Lot").get(iRecordIdx);
			String vendor = hMSHandle.getDBRecordList("vendor_name").get(iRecordIdx);
			String addDate = hMSHandle.getDBRecordList("in_store_date").get(iRecordIdx);
			int iMatInQty = Integer.parseInt(hMSHandle.getDBRecordList("IN_QTY").get(iRecordIdx));
			int moveQTY = Integer.parseInt(putQty);
			String nextBarcode = new Product_Info(new EarthquakeManagement()).GetUsedBarcode(barcode, "Semi_Pro_Storage");
			if(iMatInQty >= moveQTY)
			{
				IStorageTableInterface hStorageHandle = GenStorageHandle(nextBarcode);
				hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				if(hStorageHandle.RecordDBCount() > 0)
				{
					int hasQty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0));
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(hasQty + moveQTY),
																	Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				}
				else
				{
					hStorageHandle.AddARecord(nextBarcode, batchLot, Integer.toString(moveQTY), "0", "0", "empty", poName, vendor, addDate);
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				}
				hMSHandle.UpdateRecordByKeyList("IN_QTY", Integer.toString(iMatInQty-moveQTY), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				if (iMatInQty == moveQTY)
					hMSHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				break;
			}
			else
			{
				IStorageTableInterface hStorageHandle = GenStorageHandle(nextBarcode);
				hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				if(hStorageHandle.RecordDBCount() > 0)
				{
					int hasQty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0));
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(hasQty + iMatInQty),
																	Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				}
				else
				{
					hStorageHandle.AddARecord(nextBarcode, batchLot, Integer.toString(iMatInQty), "0", "0", "empty", poName, vendor, addDate);
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(nextBarcode, batchLot));
				}
				hMSHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				moveQTY -= iMatInQty;
			}
		}
		return hMSHandle.getDBRecordList("po_name").get(0);
	}
	
	private void MoveCurStorageToNextStorage(String barcode, String batchLot, String poName, String vendor, String addDate, String NextStorageName, int moveQTY)
	{
	}

	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
}
