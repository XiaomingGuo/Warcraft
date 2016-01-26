package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Submit_Reject_Storage_Qty_Ajax extends PageParentClass
{
	public void EnsureStorageStatusByIdAndBarcode(String recordId, String barcode)
	{
		IStorageTableInterface hDBHandle = GenStorageHandle(barcode);
		((DBTableParent) hDBHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("id"), Arrays.asList(recordId));
	}
	
	public void DeleteRecordByBarcodeAndId(String recordId, String barcode)
	{
		IStorageTableInterface hDBHandle = GenStorageHandle(barcode);
		((DBTableParent) hDBHandle).DeleteRecordByKeyList(Arrays.asList("id"), Arrays.asList(recordId));
	}
	
	public void EnsureProductStorageStatusByIdAndBarcode(String recordId, String barcode)
	{
		IStorageTableInterface hDBHandle = GenStorageHandle(barcode);
		((DBTableParent) hDBHandle).UpdateRecordByKeyList("isEnsure", "1", Arrays.asList("id"), Arrays.asList(recordId));
		hDBHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(recordId));
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		Mb_Material_Po_Record hMMPRHandle = new Mb_Material_Po_Record(new EarthquakeManagement());
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name", "vendor"),
				Arrays.asList(hDBHandle.getDBRecordList("Bar_Code").get(0),
						hDBHandle.getDBRecordList("po_name").get(0),
						hDBHandle.getDBRecordList("vendor_name").get(0)));
		hMMPRHandle.AddARecord(hMMPHandle.getDBRecordList("id").get(0), barcode, 
				hDBHandle.getDBRecordList("Batch_Lot").get(0),
				hDBHandle.getDBRecordList("IN_QTY").get(0),
				hDBHandle.getDBRecordList("in_store_date").get(0));
	}
}
