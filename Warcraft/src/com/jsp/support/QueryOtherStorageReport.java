package com.jsp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class QueryOtherStorageReport extends PageParentClass implements IPageInterface
{
    String[] m_displayArray = {"ID", "八码", "名称", "库名", "规格", "批号", "进货数量", "消耗数量", "剩余数量", "单价", "进货总价", "消耗总价", "剩余总价", "供应商", "进货单时间"};
    private List<List<String>> g_recordList, g_productInfo, g_productType;
	
	@Override
	public String[] GetDisplayArray()
	{
		return m_displayArray;
	}
	
	private List<String> GetProductBarCodeList(String storage_name, String product_type, String product_name, String supplier_name)
	{
		List<String> rtnRst = new ArrayList<String>();
		for(int iRecord = 0; iRecord < g_productInfo.get(0).size(); iRecord++)
		{
			String curVendor = g_productInfo.get(4).get(iRecord)!=null?g_productInfo.get(4).get(iRecord):"null";
			String vendorName = supplier_name.indexOf("请选择") < 0?supplier_name:curVendor;
			if(storage_name.indexOf("请选择") < 0)
			{
				if (product_type.indexOf("请选择") < 0)
				{
					if (product_name.indexOf("请选择") < 0)
					{
						if(g_productInfo.get(5).get(iRecord).equals(storage_name)&&g_productInfo.get(2).get(iRecord).equals(product_type)&&
								g_productInfo.get(0).get(iRecord).equals(product_name)&&curVendor.equals(vendorName))
							rtnRst.add(g_productInfo.get(1).get(iRecord));
					}
					else
					{
						if(g_productInfo.get(5).get(iRecord).equals(storage_name)&&g_productInfo.get(2).get(iRecord).equals(product_type)&&
								curVendor.equals(vendorName))
							rtnRst.add(g_productInfo.get(1).get(iRecord));
					}
				}
				else
				{
					if(g_productInfo.get(5).get(iRecord).equals(storage_name)&&curVendor.equals(vendorName))
						rtnRst.add(g_productInfo.get(1).get(iRecord));
				}
			}
			else
			{
				if(g_productInfo.get(5).get(iRecord).contains("五金库")&&curVendor.equals(vendorName))
					rtnRst.add(g_productInfo.get(1).get(iRecord));
			}
		}
		return rtnRst;
	}
	
    private List<List<String>> GetAllProductInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryAllRecord();
        String[] getKeyWord = new String[] {"name", "Bar_Code", "product_type", "sample_price", "sample_vendor"};
        for(int idx = 0; idx < getKeyWord.length; idx++)
            rtnRst.add(hPIHandle.getDBRecordList(getKeyWord[idx]));
        List<String> tempStoreNameList = new ArrayList<String>();
        for(int iProIdx = 0; iProIdx < rtnRst.get(2).size(); iProIdx++)
        {
        	for(int iType = 0; iType < g_productType.get(0).size(); iType++)
        	{
        		if(rtnRst.get(2).get(iProIdx).equals(g_productType.get(0).get(iType)))
        		{
        			tempStoreNameList.add(g_productType.get(1).get(iType));
        			break;
        		}
        		if(iType == g_productType.get(0).size() - 1)
        			tempStoreNameList.add("");
        	}
        }
    	rtnRst.add(tempStoreNameList);
        return rtnRst;
    }
    
    private List<List<String>> GetAllProductType()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hPTHandle = new DatabaseStore("Product_Type");
        hPTHandle.QueryAllRecord();
        String[] getKeyWord = new String[] {"name", "storeroom"};
        for(int idx = 0; idx < getKeyWord.length; idx++)
            rtnRst.add(hPTHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }

	public String GenerateResponseString(String storage_name, String product_type, String product_name, String supplier_name,
			String beginDate, String endDate, String submitDate)
	{
		String rtnRst = "";
        g_productType = GetAllProductType();
		g_productInfo = GetAllProductInfo();
	    
		List<String> bar_code_List = GetProductBarCodeList(storage_name, product_type, product_name, supplier_name);
		
		String currentDate = GenYearMonthDayString("-");
		List<String> recordList = null;
		//List<String> recordList = GetAllRecordByBarCodeList(bar_code_List);

		if(currentDate.equals(submitDate))
		{
			recordList = GetResultByStartEndDate(bar_code_List, supplier_name, beginDate, endDate);
		}
		else
		{
			recordList = GetResultBySubmitDate(bar_code_List, supplier_name, submitDate);
		}
		rtnRst += m_displayArray.length + "$";
		rtnRst += recordList.size()/m_displayArray.length+1 + "$";
		for(int idx = 0; idx < m_displayArray.length; idx++)
			rtnRst += m_displayArray[idx] + "$";
		
		double totalInPrice = 0.0, totalOutPrice = 0.0, totalRepertoryPrice = 0.0;
		int inSum=0, outSum=0, repertorySum=0;
		for(int idx = 0; idx < recordList.size(); idx++)
		{
			rtnRst += recordList.get(idx) + "$";
			if(idx%m_displayArray.length == 12)
				totalRepertoryPrice+=Double.parseDouble(recordList.get(idx));
			else if(idx%m_displayArray.length == 11)
				totalOutPrice+=Double.parseDouble(recordList.get(idx));
			else if(idx%m_displayArray.length == 10)
				totalInPrice+=Double.parseDouble(recordList.get(idx));
			else if(idx%m_displayArray.length == 8)
				repertorySum += Integer.parseInt(recordList.get(idx));
			else if(idx%m_displayArray.length == 7)
				outSum += Integer.parseInt(recordList.get(idx));
			else if(idx%m_displayArray.length == 6)
				inSum += Integer.parseInt(recordList.get(idx));
		}
		
		for(int idx = 0; idx < m_displayArray.length-10; idx++)
		{
			rtnRst += "$";
		}
		
		//{"ID", "八码", "名称", "库名", "规格", "批号", "进货数量", "消耗数量", "剩余数量", "单价", "进货总价", "消耗总价", "剩余总价", "供应商", "进货单时间"};
		NumberFormat formatter = new DecimalFormat("#.###");
		rtnRst += "汇总$"+Integer.toString(inSum)+"$";
		rtnRst += Integer.toString(outSum)+"$";
		rtnRst += Integer.toString(repertorySum)+"$";
		rtnRst += "$";
		rtnRst += formatter.format(totalInPrice)+"$";
		rtnRst += formatter.format(totalOutPrice)+"$";
		rtnRst += formatter.format(totalRepertoryPrice)+"$";
		rtnRst += "$";
		rtnRst += "$";
		return rtnRst;
	}
	
	public List<String> QueryProTypeStorage(String storageName)
	{
		DBTableParent hPTHandle = new DatabaseStore("Product_Type");
		hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("storeroom"), Arrays.asList(storageName));
		return hPTHandle.getDBRecordList("name");
	}
	
	public List<String> QueryProNameByProType(String proType)
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
		return hPIHandle.getDBRecordList("name");
	}
	
	public List<String> GetResultBySubmitDate(List<String> barcodeList, String supplier_name, String submitDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		DBTableParent hPTHandle = new DatabaseStore("Product_Type");
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcodeList.get(idx)));
			hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(hPIHandle.getDBRecordList("product_type").get(0)));
			String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
			DBTableParent hStorageHandle = GenStorageHandle(strBarcode);
			List<String> keyList = null, valueList = null;
			submitDate = submitDate.replace("-", "");
			if(supplier_name.indexOf("请选择") >= 0)
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
			for(int recordIdx=0; recordIdx < hStorageHandle.getTableInstance().RecordDBCount(); recordIdx++)
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
			for(int recordIdx=0; recordIdx < hStorageHandle.getTableInstance().RecordDBCount(); recordIdx++)
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
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		DBTableParent hPTHandle = new DatabaseStore("Product_Type");
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("Bar_Code"), Arrays.asList(barcodeList.get(idx)));
			hPTHandle.QueryRecordByFilterKeyList(Arrays.asList("name"), Arrays.asList(hPIHandle.getDBRecordList("product_type").get(0)));
			String strBarcode = hPIHandle.getDBRecordList("Bar_Code").get(0);
			DBTableParent hStorageHandle = GenStorageHandle(strBarcode);
			List<String> keyList = null, valueList = null;
			if(supplier_name.indexOf("请选择") >= 0)
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
			for(int recordIdx=0; recordIdx < hStorageHandle.getTableInstance().RecordDBCount(); recordIdx++)
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
			for(int recordIdx=0; recordIdx < hStorageHandle.getTableInstance().RecordDBCount(); recordIdx++)
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
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(proName, proType));
		return hPIHandle.getDBRecordList("Bar_Code").get(0);
	}
		
	public List<String> QueryAllBarcode()
	{
		DBTableParent hPIHandle = new DatabaseStore("Product_Info");
		hPIHandle.QueryAllRecord();
		return hPIHandle.getDBRecordList("Bar_Code");
	}
}