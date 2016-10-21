package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class QueryOther extends PageParentClass
{
    public int GetAllRecordCount()
    {
        DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
        hOSHandle.QueryAllRecord();
        return hOSHandle.getTableInstance().RecordDBCount();
    }
    
    public List<String> GetAllVendorName()
    {
        DBTableParent hOSHandle = new DatabaseStore("Vendor_Info");
        hOSHandle.QueryRecordGroupByList(Arrays.asList("vendor_name"));
        return hOSHandle.getDBRecordList("vendor_name");
    }
    
    public List<List<String>> GetOtherStorageRecordDisplay(int BeginPage, int PageRecordCount)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        DBTableParent hOSHandle = new DatabaseStore("Other_Storage");
        hOSHandle.QueryRecordByFilterKeyListWithOrderAndLimit(null, null, Arrays.asList("id"), PageRecordCount*(BeginPage-1), PageRecordCount);
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
