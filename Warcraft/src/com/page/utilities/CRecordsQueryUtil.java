package com.page.utilities;

import java.util.Arrays;
import java.util.List;

import com.Warcraft.Interface.*;

public class CRecordsQueryUtil implements IRecordsQueryUtil
{
    private ITableInterface hTableHandle;
    
    public CRecordsQueryUtil() { }

    public ITableInterface getTableHandle()
    {
        return hTableHandle;
    }

    public void setTableHandle(ITableInterface hTableHandle)
    {
        this.hTableHandle = hTableHandle;
    }
    
    public List<String> GetTableContentByKeyWord(String queryWord, String queryVal, String getKeyWord)
    {
        if(queryVal.indexOf("AllRecord") == 0)
        	hTableHandle.QueryAllRecord();
        else
        	hTableHandle.QueryRecordByFilterKeyList(Arrays.asList(queryWord), Arrays.asList(queryVal));
        return hTableHandle.getDBRecordList(getKeyWord);
    }
    
}
