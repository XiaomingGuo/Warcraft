package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.*;
import com.Warcraft.Interface.*;

public class AddMFGMaterial_ReferTo_PO extends PageParentClass
{
	String[] m_displayList = {"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "采购量", "已交数量", "未交数量", "创建时间", "状态", "操作"};
	
	public String AddMaterialToSuitedStorage(String MbMaterialPoId, String storeQty, String addDate)
	{
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordByFilterKeyList(Arrays.asList("id"), Arrays.asList(MbMaterialPoId));
		
		if(hMMPHandle.RecordDBCount() > 0)
		{
			String barcode = hMMPHandle.getDBRecordList("Bar_Code").get(0);
			String poName = hMMPHandle.getDBRecordList("po_name").get(0);
			String vendor = hMMPHandle.getDBRecordList("vendor").get(0);
			IStorageTableInterface hStorageHandle = GenProcessStorageHandle(barcode);
			String batch_lot = GenBatchLot(barcode);
			hStorageHandle.AddARecord(barcode, batch_lot, storeQty, "0", "0", "empty", poName, vendor, addDate);
		}
		return hMMPHandle.getDBRecordList("po_name").get(0);
	}
	
	private List<List<String>> GetAllMbMaterialPo(String po_name)
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
	
	private String PrepareHeader(List<List<String>> recordList)
	{
		String rtnRst = "remove$";
		if (recordList.size() > 0)
		{
			rtnRst += Integer.toString(m_displayList.length) + "$";
			rtnRst += Integer.toString(recordList.get(0).size()) + "$";
			for(int i = 0; i < m_displayList.length; i++)
			{
				rtnRst += m_displayList[i] + "$";
			}
		}
		return rtnRst;
	}
	
	public String GenerateReturnString(String po_name)
	{
		List<List<String>> recordList = GetAllMbMaterialPo(po_name);
		String rtnRst = PrepareHeader(recordList);
		
		if (recordList.size() > 0)
		{
			//"id", "Bar_Code", "po_name", "date_of_delivery", "vendor",  "PO_QTY", "create_date"
			for(int iRow = 0; iRow < recordList.get(0).size(); iRow++)
			{
				int iPurchaseCount = 0, iFinishCount = 0;
				String strBarcode = recordList.get(1).get(iRow);
				for(int iCol = 0; iCol < m_displayList.length; iCol++)
				{
					if("ID" == m_displayList[iCol])
					{
						rtnRst += Integer.toString(iRow+1) + "$";
					}
					else if("产品类型" == m_displayList[iCol])
					{
						rtnRst += GetProductInfoByBarcode(strBarcode, "product_type") + "$";
					}
					else if("产品名称" == m_displayList[iCol])
					{
						rtnRst += GetProductInfoByBarcode(strBarcode, "name") + "$";
					}
					else if("供应商" == m_displayList[iCol])
					{
						rtnRst += recordList.get(4).get(iRow) + "$";
					}
					else if("八码" == m_displayList[iCol])
					{
						rtnRst += strBarcode + "$";
					}
					else if("PO单名" == m_displayList[iCol])
					{
						rtnRst += recordList.get(2).get(iRow) + "$";
					}
					else if("交货时间" == m_displayList[iCol])
					{
						rtnRst += recordList.get(3).get(iRow) + "$";
					}
					//{"ID", "产品类型", "产品名称", "供应商", "八码", "PO单名", "交货时间", "采购量", "已交数量", "未交数量", "创建时间", "操作"};
					//{"id", "Bar_Code", "po_name", "date_of_delivery", "vendor",  "PO_QTY", "create_date"};
					else if("采购量" == m_displayList[iCol])
					{
						iPurchaseCount = Integer.parseInt(recordList.get(5).get(iRow));
						rtnRst += Integer.toString(iPurchaseCount) + "$";
					}
					else if ("已交数量" == m_displayList[iCol])
					{
						iFinishCount = GetHasFinishPurchaseNumWithoutEnsure(strBarcode, recordList.get(2).get(iRow));
						rtnRst += Integer.toString(iFinishCount) + "$";
					}
					else if ("未交数量" == m_displayList[iCol])
					{
						rtnRst += Integer.toString(iPurchaseCount-iFinishCount) + "$";
					}
					else if ("创建时间" == m_displayList[iCol])
					{
						rtnRst += recordList.get(6).get(iRow) + "$";
					}
					else if ("状态" == m_displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
					else if ("操作" == m_displayList[iCol])
					{
						rtnRst += recordList.get(0).get(iRow) + "$";
					}
				}
			}
		}
		return rtnRst;
	}
	
	public List<String> GetAllPOListNotFinishPurchange()
	{
		List<String> rtnRst = null;
		Mb_Material_Po hMMPHandle = new Mb_Material_Po(new EarthquakeManagement());
		hMMPHandle.QueryRecordGroupByList(Arrays.asList("po_name"));
		rtnRst = hMMPHandle.getDBRecordList("po_name");
		Customer_Po hCPHandle = new Customer_Po(new EarthquakeManagement());
		for (int index = 0; index < rtnRst.size(); index++)
		{
			hCPHandle.QueryRecordByFilterKeyList(Arrays.asList("po_name", "status"), Arrays.asList(rtnRst.get(index), "5"));
			if(hCPHandle.RecordDBCount() > 0)
				rtnRst.remove(rtnRst.get(index));
		}
		return rtnRst;
	}
}
