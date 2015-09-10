package com.Warcraft.Interface;

import java.util.List;

public interface IDataBaseCore
{
	public void SetDatabaseAddr(String tUrl);
	public void SetUserName(String tUserName);
	public void SetPassword(String tPassword);
	public boolean InitConnect();
	public void CloseDatabase();
	public boolean QueryDataBase(String sql);
	public boolean execUpate(String sql);
	public String GetSingleString(String keyWord);
	public int GetSingleInt(String keyWord);
	public double GetSingleDouble(String keyWord);
	public List<String> GetAllStringValue(String colName);
	public List<List<String>> GetAllDBColumnsByList(String[] colNames);
	public int GetRecordCount();
}
