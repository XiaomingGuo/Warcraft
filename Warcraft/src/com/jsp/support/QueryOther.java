package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.IPageAjaxUtil;
import com.Warcraft.Interface.IPageInterface;
import com.Warcraft.SupportUnit.DBTableParent;
import com.page.utilities.CPageAjaxUtil;

public class QueryOther extends PageParentClass implements IPageInterface
{
    private String[] m_displayArray = {"ID", "产品名称", "八码", "批号", "总进货量", "已消耗", "库存(QTY)", "单价(QTY)", "总进货价", "供应商", "备注", "操作"};
    private IPageAjaxUtil hAjaxHandle;
    private List<List<String>> g_productInfo;
    
    public QueryOther()
    {
        hAjaxHandle = new CPageAjaxUtil();
        hAjaxHandle.setTableHandle(this);
    }
    
    @Override
    public String[] GetDisplayArray()
    {
        return m_displayArray;
    }
    
    private List<List<String>> GetAllProductInfo()
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hPIHandle = new DatabaseStore("Product_Info");
        hPIHandle.QueryAllRecord();
        String[] getKeyWord = new String[] {"name", "Bar_Code", "product_type", "sample_price", "weight", "sample_vendor", "capacity", "description"};
        for(int idx = 0; idx < getKeyWord.length; idx++)
            rtnRst.add(hPIHandle.getDBRecordList(getKeyWord[idx]));
        return rtnRst;
    }
    
    // Finish End
    public String GenerateResponseString(String curDate)
    {
        g_productInfo = GetAllProductInfo();
        if(curDate.length() >= 8)
        {
            List<List<String>> recordList = GetOtherStorageRecord(curDate);
            if(recordList.size() > 0)
                return hAjaxHandle.GenerateAjaxString(recordList);
        }
        return "";
    }
    
    private String GetProductInfoByIdx(String curBarCode, int findIdx)
    {
        String rtnRst = "";
        for(int idx = 0; idx < g_productInfo.get(0).size(); idx++)
        {
            if(g_productInfo.get(1).get(idx).equals(curBarCode))
            {
                rtnRst = g_productInfo.get(findIdx).get(idx);
                break;
            }
        }
        return rtnRst;
    }
    
    public List<List<String>> GetOtherStorageRecord(String curDate)
    {
        List<List<String>> rtnRst = hAjaxHandle.GenDisplayResultList();
        List<List<String>> recordList = GetOtherStorageRecordDisplay(curDate);
        if(recordList.size() > 0)
        {
            for(int idx = 0; idx < recordList.get(0).size(); idx++)
            {
                String curBarCode = recordList.get(0).get(idx);
                rtnRst.get(0).add(Integer.toString(idx + 1));
                rtnRst.get(1).add(GetProductInfoByIdx(curBarCode, 0));
                rtnRst.get(2).add(curBarCode);
                rtnRst.get(3).add(recordList.get(1).get(idx));
                int in_QTY = Integer.parseInt(recordList.get(2).get(idx));
                rtnRst.get(4).add(recordList.get(2).get(idx));
                int out_QTY = Integer.parseInt(recordList.get(3).get(idx));
                rtnRst.get(5).add(recordList.get(3).get(idx));
                rtnRst.get(6).add(Integer.toString(in_QTY-out_QTY));
                rtnRst.get(7).add(recordList.get(4).get(idx));
                rtnRst.get(8).add(recordList.get(5).get(idx));
                rtnRst.get(9).add(recordList.get(6).get(idx));
                rtnRst.get(10).add(GetProductInfoByIdx(curBarCode, 7));
                rtnRst.get(11).add(recordList.get(7).get(idx)+"#"+recordList.get(8).get(idx));
            }
        }
        return rtnRst;
    }
    
    public List<String> GetAllVendorName()
    {
        DBTableParent hOSHandle = new DatabaseStore("Vendor_Info");
        hOSHandle.QueryRecordGroupByList(Arrays.asList("vendor_name"));
        return hOSHandle.getDBRecordList("vendor_name");
    }
    
    public List<List<String>> GetOtherStorageRecordDisplay(String curDate)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
        hOSHandle.QueryRecordByFilterKeyList(Arrays.asList("isEnsure"), Arrays.asList("0"));
        if (hOSHandle.getTableInstance().RecordDBCount() > 0)
        {
            String[] sqlKeyList = {"Bar_Code", "Batch_Lot", "IN_QTY", "OUT_QTY", "Price_Per_Unit", "Total_Price", "vendor_name", "id", "isEnsure"};
            for(int idx=0; idx < sqlKeyList.length; idx++)
             rtnRst.add(hOSHandle.getDBRecordList(sqlKeyList[idx]));
        }
        return rtnRst;
    }
    
    public boolean UpdateOtherStorageTableRecordByKeyList(String setKeyWord, String setValue, List<String> keyList, List<String> valueList)
    {
        try
        {
            DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
            hOSHandle.UpdateRecordByKeyList(setKeyWord, setValue, keyList, valueList);
        }
        catch(Exception ex)
        {
            return false;
        }
        return true;
    }
}
