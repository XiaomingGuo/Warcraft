package com.Warcraft.Interface;

public interface IEQManagement
{
	public boolean EQQuery(String hql);
	public boolean EQQueryWithLimit(String hql, int iStart, int iCount);
	public void setTableHandle(ITableInterface hTableHandle);
	public void addANewRecord();
	public void updateRecord(String hql);
	public boolean DeleteAndUpdateRecord(String hql);
}
