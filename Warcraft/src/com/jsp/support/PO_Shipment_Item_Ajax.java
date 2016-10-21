package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class PO_Shipment_Item_Ajax extends PageParentClass
{
	public List<List<String>> GetCustomerPoRecordList(String POName)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hCPRHandle = new DatabaseStore("Customer_Po_Record");
		hCPRHandle.QueryRecordByFilterKeyListOrderbyListASC(Arrays.asList("po_name"), Arrays.asList(POName), Arrays.asList("id"));
		if (hCPRHandle.getTableInstance().RecordDBCount() > 0)
		{
			String[] sqlKeyList = {"Bar_Code", "po_name", "delivery_date", "QTY", "OUT_QTY", "percent"};
			for(int idx=0; idx < sqlKeyList.length; idx++)
			{
				rtnRst.add(hCPRHandle.getDBRecordList(sqlKeyList[idx]));
			}
		}
		return rtnRst;
	}
	
	public List<String> GetProductInfoList(String barcode)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(hPIHandle.GetUsedBarcode(barcode, "Product_Storage")));
		rtnRst.add(hPIHandle.getDBRecordList("Bar_Code").get(0));
		rtnRst.add(hPIHandle.getDBRecordList("product_type").get(0));
		rtnRst.add(hPIHandle.getDBRecordList("name").get(0));
		return rtnRst;
	}
	
	public int GetInProcessRepertory(String strBarcode, String po_name)
	{
		DBTableParent hMSHandle = new DatabaseStore("Material_Storage");
		DBTableParent hPSHandle = new DatabaseStore("Product_Storage");
		DBTableParent hSPSHandle = new DatabaseStore("Semi_Product_Storage");
		return hMSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Material_Storage"), po_name)) +
				hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Product_Storage"), po_name)) +
				hSPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Semi_Pro_Storage"), po_name));
	}
	
	public int GetReleaseRepertory(String strBarcode, String po_name)
	{
		DBTableParent hPSHandle = new DatabaseStore("Product_Storage");
		return hPSHandle.GetRepertoryByKeyList(Arrays.asList("Bar_Code", "po_name", "isEnsure"), Arrays.asList(GetUsedBarcode(strBarcode, "Product_Storage"), po_name, "1"));
	}
	
	public int GetMBPurchaseOrderQty(String strBarcode, String po_name)
	{
		DBTableParent hMMPHandle = new DatabaseStore("Mb_Material_Po");
		return hMMPHandle.GetIntSumOfValue("PO_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Material_Storage"), po_name)) +
				hMMPHandle.GetIntSumOfValue("PO_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Product_Storage"), po_name)) +
				hMMPHandle.GetIntSumOfValue("PO_QTY", Arrays.asList("Bar_Code", "po_name"), Arrays.asList(GetUsedBarcode(strBarcode, "Semi_Pro_Storage"), po_name));
	}
}
