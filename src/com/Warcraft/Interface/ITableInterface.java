package com.Warcraft.Interface;

import java.util.List;

import org.hibernate.Query;

import com.DB.operation.EarthquakeManagement;

public interface ITableInterface
{
	public List<String> getDBRecordList(String keyWord);
	public void sethEQMHandle(IEQManagement hEQMHandle);
	public IEQManagement gethEQMHandle();
	public void setResult(Query query);
}