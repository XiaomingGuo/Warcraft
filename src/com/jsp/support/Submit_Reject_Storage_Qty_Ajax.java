package com.jsp.support;

import java.util.Arrays;
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
}
