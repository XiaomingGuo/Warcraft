package com.Warcraft.Interface;

public interface IEQManagement
{
	public boolean EQQuery(String hql);
	public void setTableHandle(ITableInterface hTableHandle);
	public void addANewRecord();
	public void updateRecord(String hql);
}
