package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Submit_Reject_Storage_Qty_Ajax extends PageParentClass
{
	private void EnsureStorageStatusByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		IStorageTableInterface hDBHandle = GenStorageHandle(barcode);
		((DBTableParent) hDBHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
	}
	
	public void DeleteRecordByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		IStorageTableInterface hProcessDBHandle = GenProcessStorageHandle(barcode);
		hProcessDBHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		if(hProcessDBHandle.RecordDBCount() <= 0)
		{
			IStorageTableInterface hDBHandle = GenStorageHandle(barcode);
			((DBTableParent) hDBHandle).DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "isEnsure"), Arrays.asList(barcode, batchLot, "0"));
		}
		else
			((DBTableParent) hProcessDBHandle).DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "isEnsure"), Arrays.asList(barcode, batchLot, "0"));
	}
	
	public void EnsureProductStorageStatusByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		IStorageTableInterface hProcessDBHandle = GenProcessStorageHandle(barcode);
		hProcessDBHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		if(hProcessDBHandle.RecordDBCount() > 0)
		{
			((DBTableParent)hProcessDBHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
			if(!new PageParentClass().IsOtherBarcode(barcode))
			{
				Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
				Mb_Material_Po_Record hMMPRHandle = new Mb_Material_Po_Record(new EarthquakeManagement());
				hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "vendor"),
						Arrays.asList(hProcessDBHandle.getDBRecordList("Bar_Code").get(0),
								hProcessDBHandle.getDBRecordList("po_name").get(0),
								hProcessDBHandle.getDBRecordList("vendor_name").get(0)));
				hMMPRHandle.AddARecord(hMMPHandle.getDBRecordList("id").get(0), barcode, 
						hProcessDBHandle.getDBRecordList("Batch_Lot").get(0),
						hProcessDBHandle.getDBRecordList("IN_QTY").get(0),
						hProcessDBHandle.getDBRecordList("in_store_date").get(0));
			}
		}
		else
			EnsureStorageStatusByBarcodeAndBatchLot(barcode, batchLot);
	}
}
