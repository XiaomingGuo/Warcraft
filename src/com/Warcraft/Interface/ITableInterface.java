package com.Warcraft.Interface;

import java.util.List;

import org.hibernate.Query;

public interface ITableInterface
{
	public List<String> getDBRecordList(String keyWord);
	public void setResultList(Query query);
	public Object getAWriteRecord();
	public int RecordDBCount();
}
