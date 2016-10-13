package com.page.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.EarthquakeManagement;
import com.DB.operation.User_Info;
import com.Warcraft.Interface.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class CRecordsQueryUtil implements IRecordsQueryUtil
{
    private DBTableParent hDBHandle;
    
    public CRecordsQueryUtil() { }
    
    @Override
    public DBTableParent getDBHandle()
    {
        return hDBHandle;
    }
    
    @Override
    public void setDBHandle(DBTableParent hDBHandle)
    {
        this.hDBHandle = hDBHandle;
    }
    
    @Override
    public List<String> GetTableContentByKeyWord(String queryWord, String queryVal, String getKeyWord)
    {
        if(queryVal.indexOf("AllRecord") == 0)
            hDBHandle.QueryAllRecord();
        else
            hDBHandle.QueryRecordByFilterKeyList(Arrays.asList(queryWord), Arrays.asList(queryVal));
        return hDBHandle.getTableInstance().getDBRecordList(getKeyWord);
    }
    
    @Override
    public List<String> GetTableContentGroupByKeyWord(String queryWord, String queryVal, String groupByKeyWord, String getKeyWord)
    {
        if(queryVal.indexOf("AllRecord") == 0)
            hDBHandle.QueryRecordGroupByList(Arrays.asList(groupByKeyWord));
        else
            hDBHandle.QueryRecordByFilterKeyListGroupByList(Arrays.asList(queryWord), Arrays.asList(queryVal), Arrays.asList(groupByKeyWord));
        return hDBHandle.getTableInstance().getDBRecordList(getKeyWord);
    }
    
    @Override
    public List<List<String>> GetAllTableContent(String[] getKeyList)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        hDBHandle.QueryAllRecord();
        
        if (hDBHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < getKeyList.length; idx++)
                rtnRst.add(hDBHandle.getDBRecordList(getKeyList[idx]));
        }
        return rtnRst;
    }
    
    @Override
    public List<List<String>> GetAllTableContentByKeyWord(String[] getKeyList, String keyWord, String keyVal)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        hDBHandle.QueryRecordByFilterKeyList(Arrays.asList(keyWord), Arrays.asList(keyVal));
        
        if (hDBHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < getKeyList.length; idx++)
                rtnRst.add(hDBHandle.getDBRecordList(getKeyList[idx]));
        }
        return rtnRst;
    }
}
