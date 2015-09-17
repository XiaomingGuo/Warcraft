package com.Warcraft.Interface;

public interface IDBExecute
{
	public String getTableName();
	public String[] getTableKeyList();
	public String getWhereString();
	public String getOrderByString();
	public String getLimitString();
}
