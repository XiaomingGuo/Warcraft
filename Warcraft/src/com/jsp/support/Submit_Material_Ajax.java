package com.jsp.support;

import java.util.Arrays;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class Submit_Material_Ajax extends PageParentClass
{
	public void AddARecordToStorage(String appBarcode, String batch_lot, String appProductQTY, String appPriceUnit,
			String appTotalPrice, String appSupplier_name, String appInStoreDate, String appDescription)
	{
		DBTableParent hStorageHandle = GenStorageHandle(appBarcode);
		AddSingleOtherManuRecordToStorage(hStorageHandle, appBarcode, batch_lot, appProductQTY, appPriceUnit, appTotalPrice, "empty", "Material_Supply", appSupplier_name, appInStoreDate);
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.UpdateRecordByKeyList("sample_price", appPriceUnit, Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		hPIHandle.UpdateRecordByKeyList("sample_vendor", appSupplier_name, Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
		hPIHandle.UpdateRecordByKeyList("description", appDescription, Arrays.asList("Bar_Code"), Arrays.asList(appBarcode));
	}
}
