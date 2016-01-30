package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Transfer_To_Product_Ajax extends PageParentClass
{
	public String ExecuteTransferSemiProductToProduct(String barcode, String poName, String putQty)
	{
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		hSPSHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, poName, "1"));
		
		for(int iRecordIdx = 0; iRecordIdx < hSPSHandle.RecordDBCount(); iRecordIdx++)
		{
			String batchLot = hSPSHandle.getDBRecordList("Batch_Lot").get(iRecordIdx);
			String vendor = hSPSHandle.getDBRecordList("vendor_name").get(iRecordIdx);
			String addDate = hSPSHandle.getDBRecordList("in_store_date").get(iRecordIdx);
			int iMatInQty = Integer.parseInt(hSPSHandle.getDBRecordList("IN_QTY").get(iRecordIdx));
			int moveQTY = Integer.parseInt(putQty);
			String nextBarcode = new Product_Info(new EarthquakeManagement()).GetUsedBarcode(barcode, "Product_Storage");
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
				hSPSHandle.UpdateRecordByKeyList("IN_QTY", Integer.toString(iMatInQty-moveQTY), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				if (iMatInQty == moveQTY)
					hSPSHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
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
				hSPSHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				moveQTY -= iMatInQty;
			}
		}
		return hSPSHandle.getDBRecordList("po_name").get(0);
	}
}
