package com.jsp.support;

import java.util.Arrays;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Submit_New_Material_Info_Ajax extends PageParentClass
{
	public boolean CheckBarcodeStatus(String barcode)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		String[] storageList = new String[] {"Material_Storage", "Product_Storage", "Semi_Pro_Storage", "Other_Storage"};
		int recordCount = 0;
		for(String storageName : storageList)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(GetUsedBarcode(barcode, storageName)));
			recordCount += hPIHandle.RecordDBCount();
		}
		return recordCount > 0?false:true;
	}
	
	public void AddNewProductInfo(String appBarcode, String appProductname, String appProduct_type, String productWeight, String appWeightUnit, String appDescription)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("原锭", ""),
				productWeight, appDescription);
		hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type,
				appWeightUnit, appDescription);
		hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type.replace("原锭", "半成品"),
				"0", appDescription);
	}
	
	public void AddNewOtherInfo(String appBarcode, String appProductname, String appProduct_type, String productWeight, String appWeightUnit, String appDescription)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.AddARecord(hPIHandle.GetUsedBarcode(appBarcode, "other_storage"), appProductname, appProduct_type,
				"0", appDescription);
	}
}
