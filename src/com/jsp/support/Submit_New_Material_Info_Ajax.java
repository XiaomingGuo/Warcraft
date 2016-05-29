package com.jsp.support;

import com.DB.operation.*;

public class Submit_New_Material_Info_Ajax extends PageParentClass
{
	private void AddNewManufactoryMaterialInfo(String appBarcode, String appProductname, String appProduct_type, String productWeight, String appWeightUnit, String appDescription)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		if(!IsOtherBarcode(appBarcode))
		{
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("原锭", ""),
					productWeight, "0", "0", appDescription);
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type,
					appWeightUnit, "0", "0", appDescription);
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type.replace("原锭", "半成品"),
					"0", "0", "0", appDescription);
		}
		else
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "other_storage"), appProductname, appProduct_type,
					"0", "0", "0", appDescription);
	}
	
	public String AddProductInfoRecord(String appStore_name, String appBarcode, String appProduct_type, String appProductname, String appWeightUnit, String productWeight, String appDescription)
	{
		if(appStore_name.indexOf("原材料库") >= 0)
		{
			if(!IsMaterialBarcode(appBarcode))
			{
				return "error:输入的原材料库八码必须介于[50000000 ~ 60000000)你不知道吗?";
			}
			appProduct_type = appProduct_type.contains("原锭")?appProduct_type:appProduct_type+"原锭";
			appProduct_type = appProduct_type.contains("半成品")?appProduct_type.replace("半成品", "原锭"):appProduct_type;
		}
		else if(appStore_name.indexOf("半成品库") >= 0)
		{
			if(!IsSemiProBarcode(appBarcode))
			{
				return "error:输入的半成品库八码必须介于[70000000 ~ 80000000)你不知道吗?";
			}
			appProduct_type = appProduct_type.contains("半成品")?appProduct_type:appProduct_type+"半成品";
			appProduct_type = appProduct_type.contains("原锭")?appProduct_type.replace("原锭", "半成品"):appProduct_type;
		}
		else if(appStore_name.indexOf("成品库") >= 0)
		{
			if(!IsProductBarcode(appBarcode))
			{
				return "error:输入的成品库八码必须介于[60000000 ~ 70000000)你不知道吗?";
			}
			appProduct_type = appProduct_type.contains("原锭")?appProduct_type.replace("原锭", ""):appProduct_type;
			appProduct_type = appProduct_type.contains("半成品")?appProduct_type.replace("半成品", ""):appProduct_type;
		}
		else
		{
			if(!IsOtherBarcode(appBarcode))
			{
				return "error:输入的五金库八码必须介于[50000000 ~ 79999999]你不知道吗?";
			}
		}
		AddNewManufactoryMaterialInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
		return "";
	}
}
