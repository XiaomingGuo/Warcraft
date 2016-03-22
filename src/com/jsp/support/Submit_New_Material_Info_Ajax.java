package com.jsp.support;

import com.DB.operation.*;

public class Submit_New_Material_Info_Ajax extends PageParentClass
{
	private void AddNewManufactoryMaterialInfo(String appBarcode, String appProductname, String appProduct_type, String productWeight, String appWeightUnit, String appDescription)
	{
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		if(!IsOtherBarcode(appBarcode))
		{
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "product_storage"), appProductname, appProduct_type.replace("ԭ��", ""),
					productWeight, appDescription);
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "material_storage"), appProductname, appProduct_type,
					appWeightUnit, appDescription);
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "semi_pro_storage"), appProductname, appProduct_type.replace("ԭ��", "���Ʒ"),
					"0", appDescription);
		}
		else
			hPIHandle.AddARecord(GetUsedBarcode(appBarcode, "other_storage"), appProductname, appProduct_type,
					"0", appDescription);
	}
	
	public String AddProductInfoRecord(String appStore_name, String appBarcode, String appProduct_type, String appProductname, String appWeightUnit, String productWeight, String appDescription)
	{
		if(appStore_name.indexOf("ԭ���Ͽ�") >= 0)
		{
			if(!IsMaterialBarcode(appBarcode))
			{
				return "error:Ūɶ��?ԭ���ϰ���������[50000000 ~ 60000000)֮��, �㲻֪����?";
			}
			appProduct_type = appProduct_type.contains("ԭ��")?appProduct_type:appProduct_type+"ԭ��";
		}
		else if(appStore_name.indexOf("���Ʒ��") >= 0)
		{
			if(!IsSemiProBarcode(appBarcode))
			{
				return "error:Ūɶ��?���Ʒ����������[70000000 ~ 80000000)֮��, �㲻֪����?";
			}
			appProduct_type = appProduct_type.contains("���Ʒ")?appProduct_type.replace("���Ʒ", "ԭ��"):appProduct_type+"ԭ��";
		}
		else if(appStore_name.indexOf("��Ʒ��") >= 0)
		{
			if(!IsProductBarcode(appBarcode))
			{
				return "error:Ūɶ��?��Ʒ����������[60000000 ~ 70000000)֮��, �㲻֪����?";
			}
			appProduct_type = appProduct_type.contains("ԭ��")?appProduct_type:appProduct_type+"ԭ��";
		}
		else
		{
			if(!IsOtherBarcode(appBarcode))
			{
				return "error:Ūɶ��?�����ⲻ��ʹ��[50000000 ~ 79999999]֮��İ���, �㲻֪����?";
			}
		}
		AddNewManufactoryMaterialInfo(appBarcode, appProductname, appProduct_type, productWeight, appWeightUnit, appDescription);
		return "";
	}
}
