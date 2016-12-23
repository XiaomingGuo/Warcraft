package com.jsp.support;

import java.util.Arrays;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class Submit_Reject_Storage_Qty_Ajax extends PageParentClass
{
	private void EnsureStorageStatusByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		DBTableParent hDBHandle = GenStorageHandle(barcode);
		hDBHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
	}
	
	public void DeleteRecordByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		DBTableParent hProcessDBHandle = GenProcessStorageHandle(barcode);
		hProcessDBHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		if(hProcessDBHandle.getTableInstance().RecordDBCount() <= 0)
		{
			DBTableParent hDBHandle = GenStorageHandle(barcode);
			hDBHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "isEnsure"), Arrays.asList(barcode, batchLot, "0"));
		}
		else
			hProcessDBHandle.DeleteRecordByKeyList(Arrays.asList("Bar_Code", "Batch_Lot", "isEnsure"), Arrays.asList(barcode, batchLot, "0"));
	}
	
	public void EnsureProductStorageStatusByBarcodeAndBatchLot(String barcode, String batchLot)
	{
		DBTableParent hProcessDBHandle = GenProcessStorageHandle(barcode);
		hProcessDBHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
		if(hProcessDBHandle.getTableInstance().RecordDBCount() > 0)
		{
			hProcessDBHandle.UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(barcode, batchLot));
			if(!new PageParentClass().IsOtherBarcode(barcode))
			{
				DBTableParent hMMPHandle = new DatabaseStore("Mb_Material_Po");
				DBTableParent hMMPRHandle = new DatabaseStore("Mb_Material_Po_Record");
				hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "vendor"),
						Arrays.asList(hProcessDBHandle.getDBRecordList("Bar_Code").get(0),
								hProcessDBHandle.getDBRecordList("po_name").get(0),
								hProcessDBHandle.getDBRecordList("vendor_name").get(0)));
				((Mb_Material_Po_Record)hMMPRHandle.getTableInstance()).AddARecord(hMMPHandle.getDBRecordList("id").get(0), barcode, 
						hProcessDBHandle.getDBRecordList("Batch_Lot").get(0),
						hProcessDBHandle.getDBRecordList("IN_QTY").get(0),
						hProcessDBHandle.getDBRecordList("in_store_date").get(0));
			}
		}
		else
			EnsureStorageStatusByBarcodeAndBatchLot(barcode, batchLot);
	}
}
