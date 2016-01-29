package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class AddMFGMaterial_ReferTo_PO_Ajax extends PageParentClass
{
	public String AddMaterialToSuitedStorage(String MbMaterialPoId, String storeQty, String addDate)
	{
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(MbMaterialPoId));
		
		if(hMMPHandle.RecordDBCount() > 0)
		{
			String barcode = hMMPHandle.getDBRecordList("Bar_Code").get(0);
			String poName = hMMPHandle.getDBRecordList("po_name").get(0);
			String vendor = hMMPHandle.getDBRecordList("vendor").get(0);
			IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
			String batch_lot = GenBatchLot(barcode);
			hStorageHandle.AddARecord(barcode, batch_lot, storeQty, "0", "0", "empty", poName, vendor, addDate);
		}
		return hMMPHandle.getDBRecordList("po_name").get(0);
	}
	
	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
}
