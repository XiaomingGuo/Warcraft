package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;

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
	
	public boolean IsOtherBarcode(String barcode)
	{
		if (Integer.parseInt(barcode) < 50000000 || Integer.parseInt(barcode) >= 80000000)
			return true;
		return false;
	}
	
	public boolean IsMaterialBarcode(String barcode)
	{
		if (Integer.parseInt(barcode) >= 50000000 && Integer.parseInt(barcode) < 60000000)
			return true;
		return false;
	}
	
	public boolean IsProductBarcode(String barcode)
	{
		if (Integer.parseInt(barcode) >= 60000000 && Integer.parseInt(barcode) < 70000000)
			return true;
		return false;
	}
	
	public boolean IsSemiProBarcode(String barcode)
	{
		if (Integer.parseInt(barcode) >= 70000000 && Integer.parseInt(barcode) < 80000000)
			return true;
		return false;
	}
	
	public String GetUsedBarcode(String barcode, String storage_name)
	{
		return new Product_Info(new EarthquakeManagement()).GetUsedBarcode(barcode, storage_name);
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
			rtnRst = isExhausted?"ExhaustedSemiProduct":"SemiProductStorage";
		}
		else {
			rtnRst = isExhausted?"ExhaustedOther":"OtherStorage";
		}
		return rtnRst;
	}
	
	public IStorageTableInterface GenStorageHandleByStorageName(String storageName)
	{
		if(storageName.toLowerCase().contains("other"))
			return new Other_Storage(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("material"))
			return new Material_Storage(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("product"))
			return new Product_Storage(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("semi"))
			return new Semi_Product_Storage(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("exother"))
			return new Exhausted_Other(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("exmaterial"))
			return new Exhausted_Material(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("exproduct"))
			return new Exhausted_Product(new EarthquakeManagement());
		else if(storageName.toLowerCase().contains("exsemi"))
			return new Exhausted_Semi_Product(new EarthquakeManagement());
		return null;	
	}

	public IStorageTableInterface GenStorageHandle(String barcode)
	{
		if(IsOtherBarcode(barcode))
			return new Other_Storage(new EarthquakeManagement());
		else if(IsMaterialBarcode(barcode))
			return new Material_Storage(new EarthquakeManagement());
		else if(IsProductBarcode(barcode))
			return new Product_Storage(new EarthquakeManagement());
		else if(IsSemiProBarcode(barcode))
			return new Semi_Product_Storage(new EarthquakeManagement());
		return null;	
	}
	
	public IStorageTableInterface GenExStorageHandle(String barcode)
	{
		if(IsOtherBarcode(barcode))
			return new Exhausted_Other(new EarthquakeManagement());
		else if(IsMaterialBarcode(barcode))
			return new Exhausted_Material(new EarthquakeManagement());
		else if(IsProductBarcode(barcode))
			return new Exhausted_Product(new EarthquakeManagement());
		else if(IsSemiProBarcode(barcode))
			return new Exhausted_Semi_Product(new EarthquakeManagement());
		return null;	
	}
	
	public void CheckMoveToExhaustedTable(String barcode, String batchLot)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
		hStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
		if (hStorageHandle.getDBRecordList("IN_QTY").equals(hStorageHandle.getDBRecordList("OUT_QTY")))
		{
			String orderKeyWord = "Order_Name", PoKeyWord = "po_name";
			if (GetStorageNameByBarCode(barcode, true).contains("Other"))
			{
				orderKeyWord = "vendor_name";
				PoKeyWord = "vendor_name";
			}
			IStorageTableInterface hExStorageHandle = GenExStorageHandle(barcode);
			hExStorageHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
			if(hExStorageHandle.RecordDBCount() > 0)
			{
				int setValue = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(0)) + Integer.parseInt(hExStorageHandle.getDBRecordList("IN_QTY").get(0));
				((ITableInterface)hExStorageHandle).UpdateRecordByKeyList("IN_QTY", Integer.toString(setValue), Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
				((ITableInterface)hExStorageHandle).UpdateRecordByKeyList("OUT_QTY", Integer.toString(setValue), Arrays.asList("Bar_code", "batch_lot"), Arrays.asList(barcode, batchLot));
			}
			else
				hExStorageHandle.AddAExRecord(hStorageHandle.getDBRecordList("id").get(0), hStorageHandle.getDBRecordList("Bar_Code").get(0), 
						hStorageHandle.getDBRecordList("Batch_Lot").get(0), hStorageHandle.getDBRecordList("IN_QTY").get(0),
						hStorageHandle.getDBRecordList("OUT_QTY").get(0), hStorageHandle.getDBRecordList("Price_Per_Unit").get(0),
						hStorageHandle.getDBRecordList("Total_Price").get(0), hStorageHandle.getDBRecordList(orderKeyWord).get(0),
						hStorageHandle.getDBRecordList(PoKeyWord).get(0), hStorageHandle.getDBRecordList("vendor_name").get(0),
						hStorageHandle.getDBRecordList("in_store_date").get(0), hStorageHandle.getDBRecordList("isEnsure").get(0),
						hStorageHandle.getDBRecordList("create_date").get(0));
			((ITableInterface)hStorageHandle).DeleteRecordByKeyWord("Id", Arrays.asList(hStorageHandle.getDBRecordList("id").get(0)));
		}
	}
	
	public double GetPrice_Pre_Unit(String bar_code, String Batch_Lot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(bar_code);
		IStorageTableInterface hExHandle = GenExStorageHandle(bar_code);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
		hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "Batch_Lot"), Arrays.asList(bar_code, Batch_Lot));
		List<String> tempList = new ArrayList<String>();
		tempList.addAll(hHandle.getDBRecordList("Price_Per_Unit"));
		tempList.addAll(hExHandle.getDBRecordList("Price_Per_Unit"));
		return Double.parseDouble(tempList.get(0));
	}
	
	public int GetAStorageRepertoryByPOName(String barcode, String po_name)
	{
		int rtnRst = 0;
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		rtnRst += ((DBTableParent)hHandle).GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, po_name, "1"));
		return rtnRst;
	}
	
	private int GetProductOtherPoNotDepleteRepertory(String strBarcode, String po_name)
	{
		int rtnRst = 0;
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		Product_Storage hPSHandle = new Product_Storage(new EarthquakeManagement());
		hPSHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("Bar_Code", "isEnsure"), Arrays.asList(strBarcode, "1"), Arrays.asList("po_name"));
		List<String> loopList = hPSHandle.getDBRecordList("po_name");
		loopList.remove("Material_Supply");
		loopList.remove(po_name);
		for (String poName : loopList)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(poName));
			if(Integer.parseInt(hCPHandle.getDBRecordList("status").get(0)) >= 5)
			{
				rtnRst += GetAStorageRepertoryByPOName(strBarcode, poName);
			}
		}
		return rtnRst;
	}
	
	public int GetProductRepertory(String strBarcode, String po_name)
	{
		String curBarcode = GetUsedBarcode(strBarcode, "product_storage");
		return GetAStorageRepertoryByPOName(strBarcode, "Material_Supply") +
				GetAStorageRepertoryByPOName(strBarcode, po_name) +
				GetProductOtherPoNotDepleteRepertory(curBarcode, po_name);
	}

	public int GetAllRepertoryByPOName(String barcode, String po_name)
	{
		int rtnRst = 0;
		rtnRst += GetProductRepertory(barcode, po_name);
		rtnRst += GetAStorageRepertoryByPOName(GetUsedBarcode(barcode, "Semi_Pro_Storage"), po_name);
		rtnRst += GetAStorageRepertoryByPOName(GetUsedBarcode(barcode, "Material_Storage"), po_name);
		return rtnRst;
	}
	
	public String GenYearMonthDayString()
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d%02d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1, mData.get(Calendar.DAY_OF_MONTH));
		return rtnRst;
	}

	public String GenYearMonthDayString(String strSpan)
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d%s%02d%s%02d", mData.get(Calendar.YEAR), strSpan, mData.get(Calendar.MONDAY)+1, strSpan, mData.get(Calendar.DAY_OF_MONTH));
		return rtnRst;
	}
	public String GenYearMonthString()
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d%02d", mData.get(Calendar.YEAR), mData.get(Calendar.MONDAY)+1);
		return rtnRst;
	}

	public String GenYearMonthString(String strSpan)
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d%s%02d%s", mData.get(Calendar.YEAR), strSpan, mData.get(Calendar.MONDAY)+1, strSpan);
		return rtnRst;
	}
	
	public String GenYearString()
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d", mData.get(Calendar.YEAR));
		return rtnRst;
	}

	public String GenYearString(String strSpan)
	{
		Calendar mData = Calendar.getInstance();
		String rtnRst = String.format("%04d%s", mData.get(Calendar.YEAR), strSpan);
		return rtnRst;
	}
	
	private int GetExAndStorageRecordCount(String strBarcode, String Batch_Lot)
	{
		IStorageTableInterface hHandle = GenStorageHandle(strBarcode);
		IStorageTableInterface hExHandle = GenExStorageHandle(strBarcode);
		hHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(strBarcode, Batch_Lot));
		hExHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_code", "Batch_Lot"), Arrays.asList(strBarcode, Batch_Lot));
		return hHandle.RecordDBCount() + hExHandle.RecordDBCount();
	}
	
	public String GenBatchLot(String strBarcode)
	{
		return GenBatchLot(GenYearMonthDayString(), strBarcode);
	}
	
	public String GenBatchLot(String batch_lot_Head, String strBarcode)
	{
		String rtnRst = "";
		int loopNum = 1;
		do
		{
			rtnRst = batch_lot_Head + "-" + String.format("%02d", loopNum);
			if ((GetExAndStorageRecordCount(GetUsedBarcode(strBarcode, "Product_Storage"), rtnRst) + GetExAndStorageRecordCount(GetUsedBarcode(strBarcode, "Semi_Pro_Storage"), rtnRst) +
					GetExAndStorageRecordCount(GetUsedBarcode(strBarcode, "Material_Storage"), rtnRst) + GetExAndStorageRecordCount(GetUsedBarcode(strBarcode, "Other_Storage"), rtnRst)) <= 0)
				break;
			loopNum ++;
		}while(true);
		return rtnRst;
	}
	
	public int GetHasFinishPurchaseNum(String barcode, String POName)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
		IStorageTableInterface hExStorageHandle = GenExStorageHandle(barcode);
		return hStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName)) + 
				hExStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName));
	}
	
	public String CheckRequestPara(String checkValue)
	{
		if (checkValue != null)
			checkValue.replace(" ", "");
		return checkValue;
	}
	
	public boolean CheckParamValidityMoreThanLength(String chekcVal, int valLen)
	{
		if(chekcVal != null&&chekcVal.length() > valLen)
			return true;
		return false;
	}
	
	public boolean CheckParamValidityEqualsLength(String chekcVal, int valLen)
	{
		if(chekcVal != null&&chekcVal.length() == valLen)
			return true;
		return false;
	}
	
	public boolean CheckParamValidityMoreThanValue(String chekcVal, int val)
	{
		if(chekcVal != null&&Integer.parseInt(chekcVal) > val)
			return true;
		return false;
	}
}
