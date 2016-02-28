package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Query_Material_PO_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetAllStorageRecord(String po_name)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		hMSHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name", "isEnsure"), Arrays.asList(po_name, "1"), Arrays.asList("Bar_Code"));
		if (hMSHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "po_name", "date_of_delivery", "vendor_name",  "QTY"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				if("date_of_delivery" == sqlKeyList[idx])
					rtnRst.add(GetDeliveryDate(hMSHandle.getDBRecordList("Bar_Code"), po_name));
				else if("QTY" == sqlKeyList[idx])
					rtnRst.add(GetRepertoryOfStorage(hMSHandle.getDBRecordList("Bar_Code"), po_name));
				else
					rtnRst.add(hMSHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	private List<String> GetRepertoryOfStorage(List<String> barcodeList, String po_name)
	{
		List<String> rtnRst = new ArrayList<String>();
		List<String> in_Qty_List = GetQTYOfStorage("IN_QTY", barcodeList, po_name);
		List<String> out_Qty_List = GetQTYOfStorage("OUT_QTY",  barcodeList, po_name);
		for (int idx = 0; idx < in_Qty_List.size(); idx++)
		{
			int in_qty = Integer.parseInt(in_Qty_List.get(idx));
			int out_qty = Integer.parseInt(out_Qty_List.get(idx));
			rtnRst.add(Integer.toString(in_qty - out_qty));
		}
		return rtnRst;
	}
	
	private List<String> GetQTYOfStorage(String getKeyWord, List<String> barcodeList, String po_name)
	{
		List<String> rtnRst = new ArrayList<String>();
		Material_Storage hMSHandle = new Material_Storage(new EarthquakeManagement());
		for (String barcode : barcodeList)
		{
			int curQty = hMSHandle.GetIntSumOfValue(getKeyWord, Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(barcode, po_name, "1"));
			rtnRst.add(Integer.toString(curQty));
		}
		return rtnRst;
	}

	private List<String> GetDeliveryDate(List<String> barcodeList, String po_name)
	{
		List<String> rtnRst = new ArrayList<String>();
		Customer_Po_Record hCPRHandle = new Customer_Po_Record(new EarthquakeManagement());
		for (String barcode : barcodeList)
		{
			hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, po_name));;
			if(hCPRHandle.RecordDBCount() <= 0)
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Semi_Pro_Storage"), po_name));
			rtnRst.add(hCPRHandle.getDBRecordList("delivery_date").get(0));
		}
		return rtnRst;
	}

	public int GetOUT_QTYByBarCode(String barcode)
	{
		IStorageTableInterface hHandle = GenStorageHandle(barcode);
		return hHandle.GetIntSumOfValue("OUT_QTY", Arrays.asList("Bar_Code"), Arrays.asList(barcode));
	}
}
