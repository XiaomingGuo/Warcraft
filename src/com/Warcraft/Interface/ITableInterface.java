package com.Warcraft.Interface;

import java.util.List;

import org.hibernate.Query;

public interface ITableInterface
{
	public String GetDatabaseKeyWord(String keyword);
	public List<String> getDBRecordList(String keyWord);
	public void setResultList(Query query);
	public Object getAWriteRecord();
	public int RecordDBCount();
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList);
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue, String keyWord, String keyValue);
}
