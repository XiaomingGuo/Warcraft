package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.SupportUnit.DBTableParent;
import com.jsp.support.QueryOtherStorageReport.PRODUCT_INFO;
import com.page.utilities.CPageAjaxUtil;

import java.text.NumberFormat;
import java.text.DecimalFormat;

public class MonthReport extends PageParentClass implements IPageInterface
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

    private String[] m_displayArray = {"ID", "名称", "八码", "批号", "申请人", "数量", "使用者", "单价", "总价", "申请日期", "领取确认"};
    private IPageAjaxUtil hAjaxHandle;
    private List<List<String>> g_recordList, g_productInfo, g_productType;
    
    public MonthReport()
    {
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayArray;
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
    
    private List<List<String>> GetAllOtherRecord(String beginDate, String endDate)
    {
    	List<List<String>> rtnRst = new ArrayList<List<String>>();
		String[] getKeyWord = new String[] {"Bar_Code", "Batch_Lot", "proposer", "QTY", "user_name", "apply_date"};
		
        DBTableParent hORHandle = new DatabaseStore("Other_Record");
        hORHandle.QueryRecordByFilterKeyListAndBetweenDateSpanOrderByListASC(Arrays.asList("isApprove"), Arrays.asList("1"), "apply_date", beginDate, endDate, Arrays.asList("apply_date"));
		for(int recordIdx=0; recordIdx < getKeyWord.length; recordIdx++)
			rtnRst.add(hORHandle.getDBRecordList(getKeyWord[recordIdx]));
		return rtnRst;
	}
    
    private List<String> GetProductBarCodeList(String storage_name, String product_type, String product_name)
    {
        List<String> rtnRst = new ArrayList<String>();
        for(int iRecord = 0; iRecord < g_productInfo.get(PRODUCT_INFO.name.getIndex()).size(); iRecord++)
        {
            String curBarcode = g_productInfo.get(PRODUCT_INFO.Bar_Code.getIndex()).get(iRecord);
            String curStorename = g_productInfo.get(PRODUCT_INFO.storeroom.getIndex()).get(iRecord);
            String curProductType = g_productInfo.get(PRODUCT_INFO.product_type.getIndex()).get(iRecord);
            String curProductName = g_productInfo.get(PRODUCT_INFO.name.getIndex()).get(iRecord);
            if(storage_name.indexOf("请选择") >= 0)
            {
                if(curStorename.contains("五金库"))
                    rtnRst.add(curBarcode);
            }
            else if (product_type.indexOf("请选择") >= 0)
            {
                if(curStorename.equals(storage_name))
                    rtnRst.add(curBarcode);
            }
            else if (product_name.indexOf("请选择") >= 0)
            {
                if(curStorename.equals(storage_name)&&curProductType.equals(product_type))
                    rtnRst.add(curBarcode);
            }
            else
            {
                if(curStorename.equals(storage_name)&&curProductType.equals(product_type)&&
                        curProductName.equals(product_name))
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
    
    private void InitGlobelRecord(String beginDate, String endDate)
    {
        g_productType = GetAllProductType();
        g_productInfo = GetAllProductInfo();
        g_recordList = GetAllOtherRecord(beginDate, endDate);
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
	
    public List<String> GetResultByStartEndDate(List<String> barcodeList, String user_name)
    {
        List<String> rtnRst = new ArrayList<String>();
        int iRowNum = 1;
    	for(int iRecordIdx = 0; iRecordIdx < g_recordList.get(0).size(); iRecordIdx++)
    	{
    		List<String> curProductInfo = GetCurrentProductInfoByBarcode(g_recordList.get(0).get(iRecordIdx));
    		String curUser = user_name.contains("请选择")?user_name:g_recordList.get(4).get(iRecordIdx);
    		if(barcodeList.contains(g_recordList.get(0).get(iRecordIdx))&&curUser.equals(user_name))
    		{
                rtnRst.add(Integer.toString(iRowNum));
                rtnRst.add(curProductInfo.get(PRODUCT_INFO.name.getIndex()));
                rtnRst.add(g_recordList.get(0).get(iRecordIdx));
                rtnRst.add(g_recordList.get(1).get(iRecordIdx));
                rtnRst.add(g_recordList.get(2).get(iRecordIdx));
                int in_Qty = Integer.parseInt(g_recordList.get(3).get(iRecordIdx));
                rtnRst.add(Integer.toString(in_Qty));
                rtnRst.add(g_recordList.get(4).get(iRecordIdx));
                rtnRst.add(curProductInfo.get(PRODUCT_INFO.sample_price.getIndex()));
                rtnRst.add(Double.toString(Double.parseDouble(curProductInfo.get(PRODUCT_INFO.sample_price.getIndex()))*in_Qty));
                rtnRst.add(g_recordList.get(5).get(iRecordIdx));
                rtnRst.add("已领取");
                iRowNum++;
    		}
    	}
        return rtnRst;
    }
    
    public String GenerateResponseString(String storage_name, String product_type, String product_name, String user_name, String beginDate, String endDate)
    {
        String rtnRst = "";
        InitGlobelRecord(beginDate, endDate);
        List<String> bar_code_List = GetProductBarCodeList(storage_name, product_type, product_name);
        
        List<String> recordList = GetResultByStartEndDate(bar_code_List, user_name);
        
        rtnRst += m_displayArray.length + "$";
        rtnRst += recordList.size()/m_displayArray.length+1 + "$";
        for(int idx = 0; idx < m_displayArray.length; idx++)
            rtnRst += m_displayArray[idx] + "$";
        
        double totalRepertoryPrice = 0.0;
        int inSum=0;
        for(int idx = 0; idx < recordList.size(); idx++)
        {
            rtnRst += recordList.get(idx) + "$";
            if(idx%m_displayArray.length == 5)
                inSum += Integer.parseInt(recordList.get(idx));
            else if(idx%m_displayArray.length == 8)
                totalRepertoryPrice += Double.parseDouble(recordList.get(idx));
        }
        
        for(int idx = 0; idx < m_displayArray.length-7; idx++)
        {
            rtnRst += "$";
        }
        
        //{"ID", "名称", "八码", "批号", "申请人", "数量", "使用者", "单价", "总价", "申请日期", "领取确认"};
        NumberFormat formatter = new DecimalFormat("#.###");
        rtnRst += "汇总$"+Integer.toString(inSum)+"$";
        rtnRst += "$";
        rtnRst += "总价值$";
        rtnRst += formatter.format(totalRepertoryPrice)+"$";
        rtnRst += "$";
        return rtnRst;
    }
    
    public List<String> QueryProTypeStorage(String storageName)
    {
        List<String> rtnRst = new ArrayList<String>();
        DBTableParent hHandle = GenStorageHandleByStorageName("Other_Storage");
        hHandle.QueryRecordGroupByList(Arrays.asList("Bar_Code"));
        List<String> barcodeList = hHandle.getDBRecordList("Bar_Code");
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
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
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("product_type"), Arrays.asList(proType));
        return hPIHandle.getDBRecordList("name");
    }
    
    public String QueryBarCodeByProNameAndType(String proType, String proName)
    {
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryRecordByFilterKeyList(Arrays.asList("name", "product_type"), Arrays.asList(proName, proType));
        return hPIHandle.getDBRecordList("Bar_Code").get(0);
    }
        
    public List<String> QueryAllBarcode()
    {
        DBTableParent hORHandle = new DatabaseStore("Other_Record");
        hORHandle.QueryRecordGroupByList(Arrays.asList("Bar_Code"));
        return hORHandle.getDBRecordList("Bar_Code");
    }
}
