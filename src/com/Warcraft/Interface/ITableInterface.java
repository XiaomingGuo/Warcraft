package com.Warcraft.Interface;

import java.util.List;

import org.hibernate.Query;

public interface ITableInterface
{
	public int GetIntSumOfValue(String storage_name, String getValue, String keyword, String keyValue);
	public double GetDblSumOfValue(String storage_name, String getValue, String keyword, String keyValue);
	public String GetDatabaseKeyWord(String keyword);
	public List<String> getDBRecordList(String keyWord);
	public void setResultList(Query query);
	public Object getAWriteRecord();
	public int RecordDBCount();
}
