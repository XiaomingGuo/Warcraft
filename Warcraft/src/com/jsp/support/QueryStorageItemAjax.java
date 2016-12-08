package com.jsp.support;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.SupportUnit.DBTableParent;
import com.page.utilities.CPageAjaxUtil;

public class QueryStorageItemAjax extends PageParentClass implements IPageInterface
{
    String[] m_displayArray = {"ID", "产品名称", "八码", "产品类型", "进货数量", "出库数量", "库存", "单价", "总价值"};
    private IPageAjaxUtil hAjaxHandle;
    private List<List<String>> g_recordList, g_productInfo, g_productType;
    
    public QueryStorageItemAjax()
    {
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayArray;
    }
    
    public String GenerateResponseString(String storeName, String proType, String proName, String barCode)
    {
        String rtnRst = "";
        g_recordList = GetAllOtherStorageRecord();
        g_productInfo = GetAllProductInfo();
        
        List<String> barCode_List = GetProductBarCodeList(storeName, proType, proName, barCode);
        rtnRst += GenResponseHeadString(barCode_List);
        
        rtnRst += GetDisplaySummarize(barCode_List);
        return rtnRst;
    }
    
    private String GetDisplaySummarize(List<String> barCode_List)
    {
        String rtnRst = "";
        List<String> recordList = GetAllRecordByBarCodeList(barCode_List);
        
        double totalPrice = 0.0;
        int inSum=0, outSum=0, repertorySum=0;
        for(int idx = 0; idx < recordList.size(); idx++)
        {
            rtnRst += recordList.get(idx) + "$";
            if(idx%m_displayArray.length == 8)
                totalPrice+=Double.parseDouble(recordList.get(idx));
            else if(idx%m_displayArray.length == 6)
                repertorySum += Integer.parseInt(recordList.get(idx));
            else if(idx%m_displayArray.length == 5)
                outSum += Integer.parseInt(recordList.get(idx));
            else if(idx%m_displayArray.length == 4)
                inSum += Integer.parseInt(recordList.get(idx));
        }
        
        for(int idx = 0; idx < m_displayArray.length-7; idx++)
        {
            rtnRst += "-$";
        }
        
        NumberFormat formatter = new DecimalFormat("#.###");
        rtnRst += "汇总$-$"+Integer.toString(inSum)+"$";
        rtnRst += Integer.toString(outSum)+"$";
        rtnRst += Integer.toString(repertorySum)+"$";
        rtnRst += "-$";
        rtnRst += formatter.format(totalPrice)+"$";
        return rtnRst;
    }

    private String GenResponseHeadString(List<String> barCode_List)
    {
        String rtnRst = m_displayArray.length + "$";
        rtnRst += barCode_List.size()+1 + "$";
        for(int idx = 0; idx < m_displayArray.length; idx++)
            rtnRst += m_displayArray[idx] + "$";
        return rtnRst;
    }
    
    private List<List<String>> GetAllOtherStorageRecord()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
        hOSHandle.QueryRecordByFilterKeyList(Arrays.asList("isEnsure"), Arrays.asList("1"));
        String[] getKeyWord = new String[] {"Bar_Code", "IN_QTY", "OUT_QTY", "Price_Per_Unit"};
        for(int idx = 0; idx < getKeyWord.length; idx++)
            rtnRst.add(hOSHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    private List<List<String>> GetAllProductInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryAllRecord();
        String[] getKeyWord = new String[] {"name", "Bar_Code", "product_type", "sample_price"};
        for(int idx = 0; idx < getKeyWord.length; idx++)
            rtnRst.add(hPIHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    public List<String> GetAllRecordByBarCodeList(List<String> barcodeList)
    {
        List<String> rtnRst = new ArrayList<String>();
        for (int idx = 0; idx < barcodeList.size(); idx++)
        {
            String curBarcode = barcodeList.get(idx);
            rtnRst.add(Integer.toString(idx+1));
            rtnRst.add(GetProductInfoByBarCode(curBarcode, 0));
            rtnRst.add(curBarcode);
            rtnRst.add(GetProductInfoByBarCode(curBarcode, 2));
            
            int in_Qty = GetSumOfValue(curBarcode, 1);
            int out_Qty = GetSumOfValue(curBarcode, 2);;
            double perProPrice = Double.parseDouble(GetProductInfoByBarCode(curBarcode, 3));
            double totalPrice = GetTotalPriceOfStorage(curBarcode);
            rtnRst.add(Integer.toString(in_Qty));
            rtnRst.add(Integer.toString(out_Qty));
            rtnRst.add(Integer.toString(in_Qty-out_Qty));
            NumberFormat formatter = new DecimalFormat("#.####");
            rtnRst.add(formatter.format(perProPrice));
            rtnRst.add(formatter.format(totalPrice));
        }
        return rtnRst;
    }
    
    private double GetTotalPriceOfStorage(String barCode)
    {
        double rtnRst = 0.0;
        
        for(int idx = 0; idx < g_recordList.get(0).size(); idx++)
        {
            if(g_recordList.get(0).get(idx).equals(barCode))
                rtnRst += (Integer.parseInt(g_recordList.get(1).get(idx)) - Integer.parseInt(g_recordList.get(2).get(idx))) * Double.parseDouble(g_recordList.get(3).get(idx));
        }
        return rtnRst;
    }

    private int GetSumOfValue(String barCode, int recordIdx)
    {
        int rtnRst = 0;
        
        for(int idx = 0; idx < g_recordList.get(0).size(); idx++)
        {
            if(g_recordList.get(0).get(idx).equals(barCode))
                rtnRst += Integer.parseInt(g_recordList.get(recordIdx).get(idx));
        }
        return rtnRst;
    }
    
    public List<String> GetProductBarCodeList(String storeName, String proType, String proName, String barCode)
    {
        List<String> rtnRst = new ArrayList<String>(), tempBarCodeList = new ArrayList<String>();
        if(storeName.indexOf("请选择") < 0&&proType.indexOf("请选择") < 0&&proName.indexOf("请选择") >= 0)
        {
            List<String> tempList = QueryProNameByProType(proType);
            for(int iRecordIdx = 0; iRecordIdx < tempList.size(); iRecordIdx++)
                tempBarCodeList.add(QueryBarCodeByProName(tempList.get(iRecordIdx)));
        }
        else if(storeName.indexOf("请选择") < 0&&proType.indexOf("请选择") < 0&&proName.indexOf("请选择") < 0)
        {
            tempBarCodeList.add(barCode);
        }
        
        for(int idx = 0; idx < tempBarCodeList.size(); idx++)
        {
            if(g_recordList.get(0).contains(tempBarCodeList.get(idx)))
                rtnRst.add(tempBarCodeList.get(idx));
        }
        
        return rtnRst;
    }
    
    private String QueryBarCodeByProName(String proName)
    {
        String rtnRst = "";
        for(int iQuery = 0; iQuery < g_productInfo.get(0).size(); iQuery++)
        {
            if(g_productInfo.get(0).get(iQuery).contains(proName))
            {
                rtnRst = g_productInfo.get(1).get(iQuery);
                break;
            }
        }
        return rtnRst;
    }
    
    private String GetProductInfoByBarCode(String barCode, int getKey)
    {
        String rtnRst = "";
        for(int iQuery = 0; iQuery < g_productInfo.get(0).size(); iQuery++)
        {
            if(g_productInfo.get(1).get(iQuery).contains(barCode))
            {
                rtnRst = g_productInfo.get(getKey).get(iQuery);
                break;
            }
        }
        return rtnRst;
    }
    
    private List<String> QueryProNameByProType(String proType)
    {
        List<String> rtnRst = new ArrayList<String>();
        for(int iQuery = 0; iQuery < g_productInfo.get(0).size(); iQuery++)
        {
            if(g_productInfo.get(2).get(iQuery).contains(proType))
                rtnRst.add(g_productInfo.get(0).get(iQuery));
        }
        return rtnRst;
    }
    
    public String GenOrderName(String OrderHeader)
    {
        String orderName = "";
        int iCount = 1;
        DBTableParent hPOHandle = new DatabaseStore("Product_Order");
        do
        {
            orderName = String.format("%s_%04d", OrderHeader, iCount);
            hPOHandle.QueryRecordByFilterKeyList(Arrays.asList("Order_Name"), Arrays.asList("MB_"+orderName));
            if (hPOHandle.getDBRecordList("id").size() <= 0)
                break;
            iCount += 1;
        }while(true);
        return orderName;
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
    
    public List<List<String>> GetProductTypeList(List<String> storeName)
    {
        g_productType = GetAllProductType();
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        for(int storeIdx = 0; storeIdx < storeName.size(); storeIdx++)
        {
            List<String> tempList = new ArrayList<String>();
            for(int idx = 0; idx < g_productType.get(0).size(); idx++)
            {
                if(g_productType.get(1).get(idx).contains(storeName.get(storeIdx)))
                    tempList.add(g_productType.get(0).get(idx));
            }
            rtnRst.add(tempList);
        }
        return rtnRst;
    }
}