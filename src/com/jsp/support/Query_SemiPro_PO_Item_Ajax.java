package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Query_SemiPro_PO_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetAllStorageRecord(String po_name)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		hSPSHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList("po_name", "isEnsure"), Arrays.asList(po_name, "1"), Arrays.asList("Bar_Code"));
		if (hSPSHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "po_name", "date_of_delivery", "vendor_name",  "IN_QTY"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				if("date_of_delivery" == sqlKeyList[idx])
					rtnRst.add(GetDeliveryDate(hSPSHandle.getDBRecordList("Bar_Code"), po_name));
				else if("IN_QTY" == sqlKeyList[idx])
					rtnRst.add(GetINQTYOfStorage(hSPSHandle.getDBRecordList("Bar_Code"), po_name));
				else
					rtnRst.add(hSPSHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	private List<String> GetINQTYOfStorage(List<String> barcodeList, String po_name)
	{
		List<String> rtnRst = new ArrayList<String>();
		Semi_Product_Storage hSPSHandle = new Semi_Product_Storage(new EarthquakeManagement());
		for (String barcode : barcodeList)
		{
			int curQty = hSPSHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, po_name));
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
			hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, po_name));
			if(hCPRHandle.RecordDBCount() <= 0)
				hCPRHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(hCPRHandle.GetUsedBarcode(barcode, "Material_Storage"), po_name));
			rtnRst.add(hCPRHandle.getDBRecordList("delivery_date").get(0));
		}
		return rtnRst;
	}
}
