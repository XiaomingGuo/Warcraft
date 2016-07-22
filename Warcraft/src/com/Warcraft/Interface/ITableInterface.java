package com.Warcraft.Interface;

import java.util.List;

import org.hibernate.Query;

public interface ITableInterface
{
	public String GetDatabaseKeyWord(String keyword);
	public String GetTableName();
	public List<String> getDBRecordList(String keyWord);
	public void setResultList(Query query);
	public Object getAWriteRecord();
	public int RecordDBCount();
    public void QueryAllRecord();
	public void UpdateRecordByKeyList(String setKeyWord, String setValue, List<String> keyList, List<String> valueList);
	public void QueryRecordByFilterKeyList(List<String> keyList, List<String> valueList);
	public void DeleteRecordByKeyList(List<String> keyList, List<String> valueList);
}
