package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class Submit_Material_Ajax extends PageParentClass
{
	public void AddARecordToStorage(String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit, String appTotalPrice, String appSupplier_name, String appInStoreDate)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(appBarcode);
		hStorageHandle.AddARecord(appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, "empty", "Material_Supply", appSupplier_name, appInStoreDate);
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.UpdateRecordByKeyList("sample_price", appPriceUnit, Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		hPIHandle.UpdateRecordByKeyList("sample_vendor", appSupplier_name, Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
	}
}
