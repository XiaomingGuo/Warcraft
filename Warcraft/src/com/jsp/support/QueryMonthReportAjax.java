package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.*;

public class QueryMonthReportAjax extends PageParentClass
{
	public List<String> QueryProTypeStorage(String storageName)
	{
		List<String> rtnRst = new ArrayList<String>();
		IStorageTableInterface hHandle = GenStorageHandleByStorageName(storageName);
		((DBTableParent)hHandle).QueryRecordGroupByList(Arrays.asList("Bar_Code"));
		List<String> barcodeList = hHandle.getDBRecordList("Bar_Code");
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		for(String barcode : barcodeList)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcode));
			String proType = hPIHandle.getDBRecordList("product_type").get(0);
			if(!rtnRst.contains(proType))
				rtnRst.add(proType);
		}
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
		
	public List<String> GetResultByStartEndDate(List<String> barcodeList, String user_name, String beginDate, String endDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		Product_Info hPIHandle = new Product_Info(new EarthquakeManagement());
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			String strBarcode = barcodeList.get(idx);
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(strBarcode));
			Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
			List<String> keyList = null, valueList = null;
			if(user_name.indexOf("请选择") >= 0)
			{
				keyList = Arrays.asList("Bar_Code");
				valueList = Arrays.asList(strBarcode);
			}
			else
			{
				keyList = Arrays.asList("Bar_Code", "user_name");
				valueList = Arrays.asList(strBarcode, user_name);
			}
			hORHandle.QueryRecordByFilterKeyListAndBetweenDateSpan(keyList, valueList, "create_date", beginDate, endDate);
			for(int recordIdx=0; recordIdx < hORHandle.RecordDBCount(); recordIdx++)
			{
				String strCurBatchLot = hORHandle.getDBRecordList("Batch_Lot").get(recordIdx);
				if(null == strCurBatchLot)
					continue;
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
				rtnRst.add(strBarcode);
				rtnRst.add(strCurBatchLot);
				rtnRst.add(hORHandle.getDBRecordList("proposer").get(recordIdx));
				int in_Qty = Integer.parseInt(hORHandle.getDBRecordList("QTY").get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(hORHandle.getDBRecordList("user_name").get(recordIdx));
				rtnRst.add(Double.toString(GetPrice_Pre_Unit(strBarcode, strCurBatchLot)));
				rtnRst.add(Double.toString(GetPrice_Pre_Unit(strBarcode, strCurBatchLot)*in_Qty));
				rtnRst.add(hORHandle.getDBRecordList("create_date").get(recordIdx));
				if(hORHandle.getDBRecordList("isApprove").get(recordIdx).contains("1"))
					rtnRst.add("已领取");
				else
					rtnRst.add("未领取");
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
		Other_Record hORHandle = new Other_Record(new EarthquakeManagement());
		((DBTableParent)hORHandle).QueryRecordGroupByList(Arrays.asList("Bar_Code"));
		rtnRst = hORHandle.getDBRecordList("Bar_Code");
		return rtnRst;
	}
}