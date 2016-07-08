package com.Warcraft.Interface;

import java.util.List;

public interface IRecordsQueryUtil
{
    public ITableInterface getTableHandle();
    public void setTableHandle(ITableInterface hTableHandle);
    public List<String> GetTableContentByKeyWord(String queryWord, String queryVal, String getKeyWord);
}
