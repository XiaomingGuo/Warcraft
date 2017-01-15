package com.Warcraft.SupportUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.Interface.IAllTableRecord;

public class CQueryAllTableRecord implements IAllTableRecord
{
    private List<List<String>> GetRecordByKeylist(DBTableParent hTBHandle, String[] getKeyWord)
    {
        List<List<String>> rtnRst = new ArrayList<List<String>>();
        if(null != hTBHandle&&getKeyWord.length>0)
        {
            if(hTBHandle.getTableInstance().RecordDBCount() > 0)
            {
                for(int idx=0; idx < getKeyWord.length; idx++)
                    rtnRst.add(hTBHandle.getDBRecordList(getKeyWord[idx]));
            }
        }
        return rtnRst;
    }
    
    @Override
    public List<List<String>> User_Info_AllWithoutRoot(String[] getKeyArray)
    {
        List<List<String>> rtnRst = User_Info_All(getKeyArray);
        if(rtnRst.get(0).contains("root"))
            rtnRst.get(0).remove("root");
        if(rtnRst.get(1).contains("99999"))
            rtnRst.get(1).remove("99999");
        return rtnRst;
    }
    
    @Override
    public List<List<String>> User_Info_All(String[] getKeyArray)
    {
        DBTableParent hTBHandle = new DatabaseStore("User_Info");
        hTBHandle.QueryRecordByFilterKeyList(Arrays.asList("isAbsense"), Arrays.asList("1"));
        return GetRecordByKeylist(hTBHandle, getKeyArray);
    }
    
    @Override
    public List<List<String>> Check_In_Raw_Data_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord)
    {
        DBTableParent hTBHandle = new DatabaseStore("Check_In_Raw_Data");
        hTBHandle.QueryRecordBetweenDateSpanAndOrderByListASC(queryKeyword, queryDate.substring(0, 6) + "00", queryDate.substring(0, 6) + "32", orderList);
        return GetRecordByKeylist(hTBHandle, getKeyWord);
    }
    
    @Override
    public List<List<String>> Holiday_Mark_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord)
    {
        DBTableParent hTBHandle = new DatabaseStore("Holiday_Mark");
        hTBHandle.QueryRecordBetweenDateSpanAndOrderByListASC(queryKeyword, queryDate.substring(0, 6) + "00", queryDate.substring(0, 6) + "32", orderList);
        return GetRecordByKeylist(hTBHandle, getKeyWord);
    }
    
    @Override
    public List<List<String>> Over_Time_Record_AllByDateSpan(String queryKeyword, String queryDate, List<String> orderList, String[] getKeyWord)
    {
        DBTableParent hTBHandle = new DatabaseStore("Over_Time_Record");
        hTBHandle.QueryRecordBetweenDateSpanAndOrderByListASC(queryKeyword, queryDate.substring(0, 6) + "00", queryDate.substring(0, 6) + "32", orderList);
        return GetRecordByKeylist(hTBHandle, getKeyWord);
    }
    
    @Override
    public List<List<String>> Work_Group_Info_All(String[] getKeyArray)
    {
        DBTableParent hTBHandle = new DatabaseStore("Work_Group_Info");
        hTBHandle.QueryAllRecord();
        return GetRecordByKeylist(hTBHandle, getKeyArray);
    }
    
}
