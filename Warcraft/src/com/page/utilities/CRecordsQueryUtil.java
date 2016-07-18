package com.page.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.operation.EarthquakeManagement;
import com.DB.operation.User_Info;
import com.Warcraft.Interface.*;

public class CRecordsQueryUtil implements IRecordsQueryUtil
{
    private ITableInterface hTableHandle;
    
    public CRecordsQueryUtil() { }
    
    @Override
    public ITableInterface getTableHandle()
    {
        return hTableHandle;
    }
    
    @Override
    public void setTableHandle(ITableInterface hTableHandle)
    {
        this.hTableHandle = hTableHandle;
    }
    
    @Override
    public List<String> GetTableContentByKeyWord(String queryWord, String queryVal, String getKeyWord)
    {
        if(queryVal.indexOf("AllRecord") == 0)
            hTableHandle.QueryAllRecord();
        else
            hTableHandle.QueryRecordByFilterKeyList(Arrays.asList(queryWord), Arrays.asList(queryVal));
        return hTableHandle.getDBRecordList(getKeyWord);
    }
    
    @Override
    public List<List<String>> GetAllTableContent(String[] getKeyList)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        hTableHandle.QueryAllRecord();
        
        if (hTableHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < getKeyList.length; idx++)
                rtnRst.add(hTableHandle.getDBRecordList(getKeyList[idx]));
        }
        return rtnRst;
    }
    
    @Override
    public List<List<String>> GetAllTableContentByKeyWord(String[] getKeyList, String keyWord, String keyVal)
    {
        List<List<String>> rtnRst  = new ArrayList<List<String>>(); 
        hTableHandle.QueryRecordByFilterKeyList(Arrays.asList(keyWord), Arrays.asList(keyVal));
        
        if (hTableHandle.RecordDBCount() > 0)
        {
            for(int idx=0; idx < getKeyList.length; idx++)
                rtnRst.add(hTableHandle.getDBRecordList(getKeyList[idx]));
        }
        return rtnRst;
    }
}
