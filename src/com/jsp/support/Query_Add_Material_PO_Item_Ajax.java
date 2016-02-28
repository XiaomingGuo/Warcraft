package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class Query_Add_Material_PO_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetAllMbMaterialPo(String po_name)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name"), Arrays.asList(po_name));
		if (hMMPHandle.RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"id", "Bar_Code", "po_name", "date_of_delivery", "vendor",  "PO_QTY", "create_date"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hMMPHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	public int GetHasFinishPurchaseNum(String barcode, String POName)
	{
		IStorageTableInterface hStorageHandle = GenStorageHandle(barcode);
		return hStorageHandle.GetIntSumOfValue("IN_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(barcode, POName));
	}
}
