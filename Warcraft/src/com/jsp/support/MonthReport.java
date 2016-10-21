package com.jsp.support;

import java.util.List;

import com.DB.factory.DatabaseStore;
import com.Warcraft.SupportUnit.DBTableParent;

public class MonthReport extends PageParentClass
{
	public List<String> GetUserName(List<String> groupList)
	{
		DBTableParent hORHandle = new DatabaseStore("Other_Record");
		hORHandle.QueryRecordGroupByList(groupList);
		return hORHandle.getDBRecordList("user_name");
	}
}
