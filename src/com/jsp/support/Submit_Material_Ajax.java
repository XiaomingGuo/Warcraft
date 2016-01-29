package com.jsp.support;

import com.Warcraft.Interface.IStorageTableInterface;

public class Submit_Material_Ajax extends PageParentClass
{
	public void AddARecordToStorage(String storageName, String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(appBarcode);
		hStorageHandle.AddARecord(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, "empty", "Material_Supply", appSupplier_name, appInStoreDate);
	}
}
