package com.jsp.support;

import org.hibernate.dialect.function.VarArgsSQLFunction;

import com.DB.operation.EarthquakeManagement;
import com.DB.operation.Exhausted_Material;
import com.DB.operation.Exhausted_Other;
import com.DB.operation.Exhausted_Product;
import com.DB.operation.Material_Storage;
import com.DB.operation.Other_Storage;
import com.DB.operation.Product_Storage;
import com.Warcraft.Interface.IStorageTableInterface;

public class PageParentClass
{
	public int CalcOrderQty(String po_Num, String percent)
	{
		return Integer.parseInt(po_Num) * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(int po_Num, String percent)
	{
		return po_Num * (100 + Integer.parseInt(percent))/100;
	}
	
	public int CalcOrderQty(String po_Num, int percent)
	{
		return Integer.parseInt(po_Num) * (100 + percent)/100;
	}
	
	public int CalcOrderQty(int po_Num, int percent)
	{
		return po_Num * (100 + percent)/100;
	}
	
	public String GetStorageNameByBarCode(String Bar_Code, boolean isExhausted)
	{
		String rtnRst = "";
		int barcode = Integer.parseInt(Bar_Code);
		if(barcode >= 50000000 && barcode < 60000000) {
			rtnRst = isExhausted?"ExhaustedMaterial":"MaterialStorage";
		}
		else if(barcode >= 60000000 && barcode < 70000000) {
			rtnRst = isExhausted?"ExhaustedProduct":"ProductStorage";
		}
		else if(barcode >= 70000000 && barcode < 80000000) {
			rtnRst = isExhausted?"":"";
		}
		else {
			rtnRst = isExhausted?"ExhaustedOther":"OtherStorage";
		}
		return rtnRst;
	}
	
	public IStorageTableInterface GenStorageHandle(String barcode)
	{
		int iBarcode = Integer.parseInt(barcode);
		if(iBarcode < 50000000)
			return new Other_Storage(new EarthquakeManagement());
		else if(iBarcode >= 50000000&&iBarcode < 60000000)
			return new Material_Storage(new EarthquakeManagement());
		else if(iBarcode >= 60000000&&iBarcode < 70000000)
			return new Product_Storage(new EarthquakeManagement());
		return null;	
	}
	
	public IStorageTableInterface GenExStorageHandle(String barcode)
	{
		int iBarcode = Integer.parseInt(barcode);
		if(iBarcode < 50000000)
			return new Exhausted_Other(new EarthquakeManagement());
		else if(iBarcode >= 50000000&&iBarcode < 60000000)
			return new Exhausted_Material(new EarthquakeManagement());
		else if(iBarcode >= 60000000&&iBarcode < 70000000)
			return new Exhausted_Product(new EarthquakeManagement());
		return null;	
	}
}
