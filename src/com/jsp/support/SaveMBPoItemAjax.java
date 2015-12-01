package com.jsp.support;

import java.util.ArrayList;
import java.util.List;

import com.office.core.ExcelManagment;
import com.office.operation.ExcelWrite;
import com.DB.operation.*;

public class SaveMBPoItemAjax extends PageParentClass
{
	private ExcelManagment excelUtil = null;

	public SaveMBPoItemAjax(String filePath, String fileName)
	{
		excelUtil = new ExcelManagment(new ExcelWrite(filePath, fileName));
	}
	
	public void SaveToExcelByPoName(String poName, String vendor, String delivery_Date)
	{
		List<String> writeList = new ArrayList<String>();
		Vendor_Info hVIHandle = new Vendor_Info(new EarthquakeManagement());
		hVIHandle.GetRecordByNameAndStoreroom(vendor, "Ô­²ÄÁÏ¿â");
		writeList.add(vendor);
		writeList.add(hVIHandle.getDBRecordList("vendor_fax").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_tel").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_e_mail").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_address").get(0));
		writeList.add(delivery_Date);
		
		List<List<String>> tempWriteList = new ArrayList<List<String>>();
		tempWriteList.add(writeList);
		int [] writeBegin = new int[]{9, 2};
		excelUtil.WriteDataToExcelCol("Report", tempWriteList, writeBegin);
		
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.GetRecordByPoName(poName);
		//				String[] sqlKeyList = {"Bar_Code", "PO_QTY", "date_of_delivery"};
		hMMPHandle.getDBRecordList("Bar_Code");
		hMMPHandle.getDBRecordList("PO_QTY");
		hMMPHandle.getDBRecordList("date_of_delivery");
	}
	
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
}
