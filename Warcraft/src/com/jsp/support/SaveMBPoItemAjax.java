package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.office.core.ExcelManagment;
import com.office.operation.ExcelWrite;
import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class SaveMBPoItemAjax extends PageParentClass
{
	private ExcelManagment excelUtil = null;
	private String filePath, fileName;

	public SaveMBPoItemAjax(String filePath, String fileName)
	{
		this.filePath = filePath;
		this.fileName = fileName;
	}
	
	public void SaveToExcelByPoName(String poName, String vendor, String delivery_Date)
	{
		List<String> writeList = new ArrayList<String>();
		DBTableParent hVIHandle = new DatabaseStore("Vendor_Info");
		hVIHandle.QueryRecordByFilterKeyList(Arrays.asList("vendor_name", "storeroom"), Arrays.asList(vendor, "原材料库"));
		writeList.add(vendor);
		writeList.add(hVIHandle.getDBRecordList("vendor_fax").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_tel").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_e_mail").get(0));
		writeList.add(hVIHandle.getDBRecordList("vendor_address").get(0));
		writeList.add(delivery_Date);
		
		List<List<String>> tempWriteList = new ArrayList<List<String>>();
		tempWriteList.add(writeList);
		int [] writeBegin = new int[]{9, 2};
		excelUtil = new ExcelManagment(new ExcelWrite(filePath, fileName));
		excelUtil.WriteDataToExcelCol("Report", tempWriteList, writeBegin);
	}
	
	public void SavePoRecordToExcel(List<List<String>> recordList)
	{
		int [] beginCell = new int[]{18, 1};
		excelUtil = new ExcelManagment(new ExcelWrite(filePath, fileName));
		excelUtil.WriteDataToExcelBlock("Report", recordList, beginCell);
	}
}
