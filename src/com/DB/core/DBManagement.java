package com.DB.core;

import java.util.Arrays;
import java.util.List;

import com.Warcraft.Interface.IDBExecute;
import com.Warcraft.Interface.IDataBaseCore;

public class DBManagement
{
	private IDBExecute hDBExecHandle;
	private IDataBaseCore hDBCoreHandle;
	public DBManagement(IDBExecute hDBExecHandle, IDataBaseCore hDBHandle)
	{
		this.hDBExecHandle = hDBExecHandle;
		this.hDBCoreHandle = hDBHandle;
	}
	
	private String ConnListStrByKeyword(List<String> recordList, String spanWord)
	{
		String rtnRst = "";
		for(int idx=0; idx < recordList.size(); idx++)
		{
			rtnRst += recordList.get(idx);
			if (idx != recordList.size()-1)
				rtnRst += " " + spanWord + " ";
		}
		return rtnRst;
	}

	private String GetKeyWordList(List<String> keyList)
	{
		String rtnRst = null;
		if (checkKeyWord(keyList))
		{
			rtnRst = ConnListStrByKeyword(keyList, ",");
		}
		return rtnRst;
	}

	private boolean checkKeyWord(List<String> keyList)
	{
		List<String> allDBList = Arrays.asList(hDBExecHandle.getTableKeyList());
		for(int idx = 0; idx < keyList.size(); idx++)
		{
			if (!allDBList.contains(keyList.get(idx)))
				return false;
		}
		return true;
	}

	public boolean InsertRecords(List<List<String>> recordLists)
	{
		String sql = "INSERT INTO " + hDBExecHandle.getTableName() + " (";
		if(recordLists.size() >= 2)
		{
			sql += GetKeyWordList(recordLists.get(0)) + ") VALUES (";
			for (int recordIdx=1; recordIdx < recordLists.size(); recordIdx++)
			{
				String execSql = sql + ConnListStrByKeyword(recordLists.get(recordIdx), ",") + ")";
				hDBCoreHandle.execUpate(execSql);
			}
		}
		else
		{
			return false;
		}
		return true;
	}
	
	public boolean GetRecordContent()
	{
		//List<String> keyWordList = new ArrayList<String>();
		//for (int index=0; index < filterKeyList.size(); index++)
		//	keyWordList.add(filterKeyList.get(index) + "='" + filterWordList.get(index) + "'");
		
		//String sql = "SELECT * FROM " + hDBExecHandle.getTableName() + " WHERE " + ConnListStrByKeyword(keyWordList, "AND") + " ORDER BY " + ConnListStrByKeyword(orderWordList, ",") + " DESC, ASC";
		String sql = "SELECT * FROM " + hDBExecHandle.getTableName() + hDBExecHandle.getWhereString() + hDBExecHandle.getOrderByString() + hDBExecHandle.getLimitString();
		if (hDBCoreHandle.QueryDataBase(sql)&&hDBCoreHandle.GetRecordCount()>0)
		{
			int recordCount = hDBCoreHandle.GetRecordCount();
			/*if (recordCount > 1)
				hDBExecHandle.SetRecordContent(hDBCoreHandle.Get);
			else
				hDBExecHandle.SetRecordContent(hDBCoreHandle.GetAllStringValue(colName));*/
		}
		else
		{
			hDBCoreHandle.CloseDatabase();
		}
		return true;
	}
	
	public boolean GetAllRecordOrderFilterBetween(List<Integer> filterKeyList, List<String> filterWordList, List<Integer> orderIdxList)
	{
		String sql = "SELECT * FROM " + hDBExecHandle.getTableName() + " WHERE " + " ORDER BY ";
		return true;
	}
	
}
