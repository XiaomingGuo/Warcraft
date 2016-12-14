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
    public static enum PRODUCT_INFO{name("name", 0), Bar_Code("Bar_Code", 1), product_type("product_type", 2), sample_price("sample_price", 3), sample_vendor("sample_vendor", 4), description("description", 5), storeroom("storeroom", 6);
        private String strName;
        private int index ;
        private PRODUCT_INFO(String name, int index ){
            this.strName = name;
            this.index = index ;
        }
        public int getIndex() {
            return index;
        }
        public String getName() {
            return this.strName;
        }
    }
    
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
        for(int iRecord = 0; iRecord < g_productInfo.get(PRODUCT_INFO.name.getIndex()).size(); iRecord++)
        {
            String curVendor = g_productInfo.get(PRODUCT_INFO.sample_vendor.getIndex()).get(iRecord)!=null?g_productInfo.get(PRODUCT_INFO.sample_vendor.getIndex()).get(iRecord):"null";
            String curBarcode = g_productInfo.get(PRODUCT_INFO.Bar_Code.getIndex()).get(iRecord);
            String curStorename = g_productInfo.get(PRODUCT_INFO.storeroom.getIndex()).get(iRecord);
            String curProductType = g_productInfo.get(PRODUCT_INFO.product_type.getIndex()).get(iRecord);
            String curProductName = g_productInfo.get(PRODUCT_INFO.name.getIndex()).get(iRecord);
            String sampleVendorName = supplier_name.indexOf("请选择") < 0?supplier_name:curVendor;
            if(storage_name.indexOf("请选择") >= 0)
            {
                if(curStorename.contains("五金库")&&curVendor.equals(sampleVendorName))
                    rtnRst.add(curBarcode);
            }
            else if (product_type.indexOf("请选择") >= 0)
            {
                if(curStorename.equals(storage_name)&&curVendor.equals(sampleVendorName))
                    rtnRst.add(curBarcode);
            }
            else if (product_name.indexOf("请选择") >= 0)
            {
                if(curStorename.equals(storage_name)&&curProductType.equals(product_type)&&
                        curVendor.equals(sampleVendorName))
                    rtnRst.add(curBarcode);
            }
            else
            {
                if(curStorename.equals(storage_name)&&curProductType.equals(product_type)&&
                        curProductName.equals(product_name)&&curVendor.equals(sampleVendorName))
                    rtnRst.add(curBarcode);
            }
        }
        return rtnRst;
    }
    
    private List<List<String>> GetAllProductInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryAllRecord();
        for(PRODUCT_INFO val : PRODUCT_INFO.values())
        {
            if(val.equals(PRODUCT_INFO.storeroom))
                continue;
            rtnRst.add(hPIHandle.getDBRecordList(val.getName()));
        }
        rtnRst.add(GetStoreNameOfProductInfo(rtnRst));
        return rtnRst;
    }
    
    private List<String> GetStoreNameOfProductInfo(List<List<String>> recordList)
    {
        List<String> rtnRst = new ArrayList<String>();
        for(int iProIdx = 0; iProIdx < recordList.get(PRODUCT_INFO.product_type.getIndex()).size(); iProIdx++)
        {
            for(int iType = 0; iType < g_productType.get(0).size(); iType++)
            {
                if(recordList.get(PRODUCT_INFO.product_type.getIndex()).get(iProIdx).equals(g_productType.get(0).get(iType)))
                {
                    rtnRst.add(g_productType.get(1).get(iType));
                    break;
                }
                if(iType == g_productType.get(0).size() - 1)
                    rtnRst.add("");
            }
        }
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
    
    private void InitGlobelRecord(String storeName, String beginDate, String endDate)
    {
        g_productType = GetAllProductType();
        g_productInfo = GetAllProductInfo();
        g_recordList = GetAllStorageRecord(storeName, beginDate, endDate);
    }
    
    private List<List<String>> GetAllStorageRecord(String storeName, String beginDate, String endDate)
    {
    	List<List<String>> rtnRst = new ArrayList<List<String>>();
		DBTableParent hStorageHandle = null;
		String[] getKeyWord = new String[] {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name", "in_store_date"};
		
		for(String storeVal : new String[]{GetStoreNameBySelectName(storeName), "ex" + GetStoreNameBySelectName(storeName)})
		{
			hStorageHandle = GenStorageHandleByStorageName(storeVal);
			hStorageHandle.QueryRecordBetweenDateSpanAndOrderByListASC("create_date", beginDate, endDate, Arrays.asList("create_date"));
			for(int recordIdx=0; recordIdx < getKeyWord.length; recordIdx++)
			{
				if(rtnRst.size() == getKeyWord.length)
					rtnRst.get(recordIdx).addAll(hStorageHandle.getDBRecordList(getKeyWord[recordIdx]));
				else
					rtnRst.add(hStorageHandle.getDBRecordList(getKeyWord[recordIdx]));
			}
		}
		return rtnRst;
	}
    
	private List<String> GetAllDisplayRecord(String storage_name, String product_type, String product_name, String supplier_name,
			String beginDate, String endDate, String submitDate)
	{
		List<String> rtnRst = null;
		List<String> bar_code_List = GetProductBarCodeList(storage_name, product_type, product_name, supplier_name);
		
		String currentDate = GenYearMonthDayString("-");
		if(currentDate.equals(submitDate))
			rtnRst = GetResultByStartEndDate(bar_code_List, beginDate, endDate);
		else
			rtnRst = GetResultBySubmitDate(bar_code_List, submitDate);
		return rtnRst;
	}
	
	private List<List<String>> GetAllStorageInfoByBarcode(String strBarcode, String submitDate)
	{
		List<List<String>> rtnRst = new ArrayList<List<String>>();
		String curDate = GenYearMonthDayString("-").replace("-", "");
		for(int iRecord = 0; iRecord < g_recordList.get(0).size(); iRecord++)
		{
			String cmpDate = curDate.equals(submitDate)?curDate:g_recordList.get(7).get(iRecord);
			if(g_recordList.get(0).get(iRecord).equals(strBarcode)&&cmpDate.equals(submitDate))
			{
				for(int iAddIdx = 0; iAddIdx < g_recordList.size(); iAddIdx++)
				{
					if(rtnRst.size() != g_recordList.size())
						rtnRst.add(new ArrayList<String>());
					rtnRst.get(iAddIdx).add(g_recordList.get(iAddIdx).get(iRecord));
				}
			}
		}
		return rtnRst;
	}
	
	public List<String> GetResultByStartEndDate(List<String> barcodeList, String beginDate, String endDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			String strBarcode = barcodeList.get(idx);
			List<String> curProductInfo = GetCurrentProductInfoByBarcode(strBarcode);
			NumberFormat formatter = new DecimalFormat("#.###");
			List<List<String>> tempRecord = GetAllStorageInfoByBarcode(strBarcode, GenYearMonthDayString("-").replace("-", ""));
			if(tempRecord.size() <= 0)
				continue;
			for(int recordIdx=0; recordIdx < tempRecord.get(0).size(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.name.getIndex()));
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.storeroom.getIndex()));
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.description.getIndex()));
				rtnRst.add(tempRecord.get(1).get(recordIdx));
				int in_Qty = Integer.parseInt(tempRecord.get(2).get(recordIdx));
				int out_Qty = Integer.parseInt(tempRecord.get(3).get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(tempRecord.get(4).get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(tempRecord.get(5).get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(tempRecord.get(6).get(recordIdx));
				rtnRst.add(tempRecord.get(7).get(recordIdx));
				iRowNum++;
			}
		}
		return rtnRst;
	}
	
	public List<String> GetResultBySubmitDate(List<String> barcodeList, String submitDate)
	{
		List<String> rtnRst = new ArrayList<String>();
		int iRowNum = 1;
		for (int idx = 0; idx < barcodeList.size(); idx++)
		{
			String strBarcode = barcodeList.get(idx);
			List<String> curProductInfo = GetCurrentProductInfoByBarcode(strBarcode);
			submitDate = submitDate.replace("-", "");
			NumberFormat formatter = new DecimalFormat("#.###");
			List<List<String>> tempRecord = GetAllStorageInfoByBarcode(strBarcode, submitDate);
			if(tempRecord.size() <= 0)
				continue;
			for(int recordIdx=0; recordIdx < tempRecord.get(0).size(); recordIdx++)
			{
				rtnRst.add(Integer.toString(iRowNum));
				rtnRst.add(strBarcode);
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.name.getIndex()));
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.storeroom.getIndex()));
				rtnRst.add(curProductInfo.get(PRODUCT_INFO.description.getIndex()));
				rtnRst.add(tempRecord.get(1).get(recordIdx));
				int in_Qty = Integer.parseInt(tempRecord.get(2).get(recordIdx));
				int out_Qty = Integer.parseInt(tempRecord.get(3).get(recordIdx));
				rtnRst.add(Integer.toString(in_Qty));
				rtnRst.add(Integer.toString(out_Qty));
				rtnRst.add(Integer.toString(in_Qty-out_Qty));
				float perUnitPrice = Float.parseFloat(tempRecord.get(4).get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice));
				rtnRst.add(tempRecord.get(5).get(recordIdx));
				rtnRst.add(formatter.format(perUnitPrice*out_Qty));
				rtnRst.add(formatter.format(perUnitPrice*(in_Qty-out_Qty)));
				rtnRst.add(tempRecord.get(6).get(recordIdx));
				rtnRst.add(tempRecord.get(7).get(recordIdx));
				iRowNum++;
			}
		}
		return rtnRst;
	}
	
	public String GenerateResponseString(String storage_name, String product_type, String product_name, String supplier_name,
			String beginDate, String endDate, String submitDate)
	{
		String rtnRst = "";
		InitGlobelRecord(storage_name, beginDate, endDate);
		
		List<String> recordList = GetAllDisplayRecord(storage_name, product_type, product_name, supplier_name, beginDate, endDate, submitDate);
		
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
	
	private List<String> GetCurrentProductInfoByBarcode(String barCode)
	{
		List<String> rtnRst = new ArrayList<String>();
		for(int iRecordIdx = 0; iRecordIdx < g_productInfo.get(PRODUCT_INFO.Bar_Code.getIndex()).size(); iRecordIdx++)
		{
			if(g_productInfo.get(PRODUCT_INFO.Bar_Code.getIndex()).get(iRecordIdx).equals(barCode))
			{
				for(PRODUCT_INFO val : PRODUCT_INFO.values())
					rtnRst.add(g_productInfo.get(val.getIndex()).get(iRecordIdx));
				break;
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