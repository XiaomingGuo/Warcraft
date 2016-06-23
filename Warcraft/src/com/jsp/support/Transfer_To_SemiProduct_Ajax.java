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
		
		int moveQTY = Integer.parseInt(putQty);
		for(int iRecordIdx = 0; iRecordIdx < hMSHandle.RecordDBCount(); iRecordIdx++)
		{
			String batchLot = hMSHandle.getDBRecordList("Batch_Lot").get(iRecordIdx);
			String vendor = hMSHandle.getDBRecordList("vendor_name").get(iRecordIdx);
			String addDate = hMSHandle.getDBRecordList("in_store_date").get(iRecordIdx);
			String priceUnit = hMSHandle.getDBRecordList("Price_Per_Unit").get(iRecordIdx);
			String totalPrice = hMSHandle.getDBRecordList("Total_Price").get(iRecordIdx);
			String orderName = hMSHandle.getDBRecordList("Order_Name").get(iRecordIdx);
			int iMatQty = Integer.parseInt(hMSHandle.getDBRecordList("IN_QTY").get(iRecordIdx))-Integer.parseInt(hMSHandle.getDBRecordList("OUT_QTY").get(iRecordIdx));
			String toBarcode = GetUsedBarcode(barcode, "Semi_Pro_Storage");
			if(iMatQty >= moveQTY)
			{
				IStorageTableInterface hStorageHandle = GenStorageHandle(toBarcode);
				hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				if(hStorageHandle.RecordDBCount() > 0)
				{
					int hasQty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0));
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(hasQty + moveQTY),
																	Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				}
				else
				{
					hStorageHandle.AddARecord(toBarcode, batchLot, Integer.toString(moveQTY), priceUnit, totalPrice, orderName, poName, vendor, addDate);
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				}
				int outQty = Integer.parseInt(hMSHandle.getDBRecordList("OUT_QTY").get(iRecordIdx));
				hMSHandle.UpdateRecordByKeyList("OUT_QTY", Integer.toString(outQty+moveQTY), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				
				if (iMatQty == moveQTY)
					CheckMoveToExhaustedTable(barcode, batchLot);
				break;
			}
			else
			{
				IStorageTableInterface hStorageHandle = GenStorageHandle(toBarcode);
				hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				if(hStorageHandle.RecordDBCount() > 0)
				{
					int hasQty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0));
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(hasQty + iMatQty),
																	Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				}
				else
				{
					hStorageHandle.AddARecord(toBarcode, batchLot, Integer.toString(iMatQty), priceUnit, totalPrice, orderName, poName, vendor, addDate);
					((ITableInterface) hStorageHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(toBarcode, batchLot));
				}
				int outQty = Integer.parseInt(hMSHandle.getDBRecordList("OUT_QTY").get(iRecordIdx));
				if(moveQTY > iMatQty)
					hMSHandle.UpdateRecordByKeyList("OUT_QTY", Integer.toString(outQty+iMatQty), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				else
					hMSHandle.UpdateRecordByKeyList("OUT_QTY", Integer.toString(outQty+moveQTY), Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
				CheckMoveToExhaustedTable(barcode, batchLot);
				moveQTY -= iMatQty;
			}
		}
		return hMSHandle.getDBRecordList("po_name").get(0);
	}
}
