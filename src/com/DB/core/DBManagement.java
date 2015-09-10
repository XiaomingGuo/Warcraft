package com.DB.core;

import java.util.Arrays;
import java.util.List;

import com.Warcraft.Interface.IDBExecute;
import com.Warcraft.Interface.IDataBaseCore;

public class DBManagement extends DataBaseCore
{
	private IDBExecute hDBExecHandle;
	private IDataBaseCore hDBHandle;
	public DBManagement(IDBExecute hDBExecHandle, IDataBaseCore hDBHandle)
	{
		this.hDBExecHandle = hDBExecHandle;
		this.hDBHandle = hDBHandle;
	}
	
	public boolean InsertRecords(List<List<String>> recordLists)
	{
		String sql = "INSERT INTO " + hDBExecHandle.getTableName() + " (";
		if(recordLists.size() >= 2)
		{
			sql += GetKeyWordList(recordLists.get(0)) + ") VALUES (";
			for (int recordIdx=1; recordIdx < recordLists.size(); recordIdx++)
			{
				String execSql = sql + getRecordString(recordLists.get(recordIdx)) + ")";
				hDBHandle.execUpate(execSql);
			}
		}
		else
		{
			return false;
		}
		return true;
	}

	private String getRecordString(List<String> recordList)
	{
		String rtnRst = null;
		for(int idx=0; idx < recordList.size(); idx++)
		{
			rtnRst += recordList.get(idx);
			if (idx != recordList.size()-1)
				rtnRst += ",";
		}
		return rtnRst;
	}

	private String GetKeyWordList(List<String> keyList)
	{
		String rtnRst = null;
		if (checkKeyWord(keyList))
		{
			rtnRst = getRecordString(keyList);
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

}
