package com.jsp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.IStorageTableInterface;

public class QueryStorageReportAjax extends PageParentClass
{
	public List<String> QueryProTypeStorage(String storageName)
	{
		List<String> rtnRst = null;
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		hPTHandle.GetRecordByStoreroom(storageName);
		rtnRst = hPTHandle.getDBRecordList("name");
		return rtnRst;
	}
	
	public List<String> QueryProNameByProType(String proType)
	{
		List<String> rtnRst = null;
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		rtnRst = hPIHandle.getDBRecordList("name");
		return rtnRst;
	}
	
	public List<String> GetResultBySubmitDate(List<String> barcodeList, String supplier_name, String submitDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.GetRecordByBarcode(barcodeList.get(idx));
			hPTHandle.GetRecordByName(hPIHandle.getDBRecordList("product_type").get(0));
			String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
			IStorageTableInterface hStorageHandle = GenStorageHandle(strBarcode);
			List<String> keyList = null, valueList = null;
			submitDate = submitDate.replace("-", "");
			if(supplier_name.indexOf("��ѡ��") >= 0)
			{
				keyList = Arrays.asList("Bar_Code", "in_store_date");
				valueList = Arrays.asList(strBarcode, submitDate);
			}
			else
			{
				keyList = Arrays.asList("Bar_Code", "in_store_date", "vendor_name");
				valueList = Arrays.asList(strBarcode, submitDate, supplier_name);
			}
				
			hStorageHandle.QueryRecordByFilterKeyList(keyList, valueList);
			NumberFormat formatter = new DecimalFormat("#.###");
			for(int recordIdx=0; recordIdx < hStorageHandle.RecordDBCount(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
				rtnRst.add(hPTHandle.getDBRecordList("storeroom").get(0));
				rtnRst.add(hPIHandle.getDBRecordList("description").get(0));
				rtnRst.add(hStorageHandle.getDBRecordList("Batch_Lot").get(recordIdx));
				int in_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(recordIdx));
				int out_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("OUT_QTY").get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(hStorageHandle.getDBRecordList("Price_Per_Unit").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(hStorageHandle.getDBRecordList("Total_Price").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(hStorageHandle.getDBRecordList("vendor_name").get(recordIdx));
				rtnRst.add(hStorageHandle.getDBRecordList("in_store_date").get(recordIdx));
				iRowNum++;
			}
			hStorageHandle = GenExStorageHandle(strBarcode);
			hStorageHandle.QueryRecordByFilterKeyList(keyList, valueList);
			for(int recordIdx=0; recordIdx < hStorageHandle.RecordDBCount(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
				rtnRst.add(hPTHandle.getDBRecordList("storeroom").get(0));
				rtnRst.add(hPIHandle.getDBRecordList("description").get(0));
				rtnRst.add(hStorageHandle.getDBRecordList("Batch_Lot").get(recordIdx));
				int in_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(recordIdx));
				int out_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("OUT_QTY").get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(hStorageHandle.getDBRecordList("Price_Per_Unit").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(hStorageHandle.getDBRecordList("Total_Price").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(hStorageHandle.getDBRecordList("vendor_name").get(recordIdx));
				rtnRst.add(hStorageHandle.getDBRecordList("in_store_date").get(recordIdx));
				iRowNum++;
			}
		}
		return rtnRst;
	}
	
	public List<String> GetResultByStartEndDate(List<String> barcodeList, String supplier_name, String beginDate, String endDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		//{"ID", "����", "���", "����", "���", "���", "�������", "�������", "ʣ������", "����", "����ܼ�", "����ܼ�", "ʣ���ܼ�", "��Ӧ��", "�����ʱ��"};
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		Product_Type hPTHandle = new Product_Type(new EarthquakeManagement());
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.GetRecordByBarcode(barcodeList.get(idx));
			hPTHandle.GetRecordByName(hPIHandle.getDBRecordList("product_type").get(0));
			String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
			IStorageTableInterface hStorageHandle = GenStorageHandle(strBarcode);
			List<String> keyList = null, valueList = null;
			if(supplier_name.indexOf("��ѡ��") >= 0)
			{
				keyList = Arrays.asList("Bar_Code");
				valueList = Arrays.asList(strBarcode);
			}
			else
			{
				keyList = Arrays.asList("Bar_Code", "vendor_name");
				valueList = Arrays.asList(strBarcode, supplier_name);
			}
			hStorageHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(keyList, valueList, "create_date", beginDate, endDate);
			NumberFormat formatter = new DecimalFormat("#.###");
			for(int recordIdx=0; recordIdx < hStorageHandle.RecordDBCount(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
				rtnRst.add(hPTHandle.getDBRecordList("storeroom").get(0));
				rtnRst.add(hPIHandle.getDBRecordList("description").get(0));
				rtnRst.add(hStorageHandle.getDBRecordList("Batch_Lot").get(recordIdx));
				int in_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(recordIdx));
				int out_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("OUT_QTY").get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(hStorageHandle.getDBRecordList("Price_Per_Unit").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(hStorageHandle.getDBRecordList("Total_Price").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(hStorageHandle.getDBRecordList("vendor_name").get(recordIdx));
				rtnRst.add(hStorageHandle.getDBRecordList("in_store_date").get(recordIdx));
				iRowNum++;
			}
			hStorageHandle = GenExStorageHandle(strBarcode);
			hStorageHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(keyList, valueList, "create_date", beginDate, endDate);
			for(int recordIdx=0; recordIdx < hStorageHandle.RecordDBCount(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
				rtnRst.add(hPTHandle.getDBRecordList("storeroom").get(0));
				rtnRst.add(hPIHandle.getDBRecordList("description").get(0));
				rtnRst.add(hStorageHandle.getDBRecordList("Batch_Lot").get(recordIdx));
				int in_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("IN_QTY").get(recordIdx));
				int out_Qty = Integer.parseInt(hStorageHandle.getDBRecordList("OUT_QTY").get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(hStorageHandle.getDBRecordList("Price_Per_Unit").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(hStorageHandle.getDBRecordList("Total_Price").get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(hStorageHandle.getDBRecordList("vendor_name").get(recordIdx));
				rtnRst.add(hStorageHandle.getDBRecordList("in_store_date").get(recordIdx));
				iRowNum++;
			}
		}
		return rtnRst;
	}
	
	public String QueryBarCodeByProNameAndType(String proType, String proName)
	{
		String rtnRst = "";
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(proName, proType));
		rtnRst = hPIHandle.getDBRecordList("Bar_Code").get(0);
		return rtnRst;
	}
		
	public List<String> QueryAllBarcode()
	{
		List<String> rtnRst = null;
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		hPIHandle.GetAllRecord();
		rtnRst = hPIHandle.getDBRecordList("Bar_Code");
		return rtnRst;
	}
}