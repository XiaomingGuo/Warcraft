package com.Warcraft.Interface;

import java.util.List;

public interface IRecordsQueryUtil
{
    public ITableInterface getTableHandle();
    public void setTableHandle(ITableInterface hTableHandle);
    public List<String> GetTableContentByKeyWord(String queryWord, String queryVal, String getKeyWord);
    public List<List<String>> GetAllTableContent(String[] getKeyList);
    public List<List<String>> GetAllTableContentByKeyWord(String[] sqlGetArray, String keyWord, String keyVal);
    public List<String> GetTableContentGroupByKeyWord(String queryWord, String queryVal, String groupByKeyWord, String getKeyWord);
}
