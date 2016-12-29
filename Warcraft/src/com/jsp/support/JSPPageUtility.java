package com.jsp.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.DB.factory.DatabaseStore;
import com.DB.operation.*;
import com.Warcraft.SupportUnit.DBTableParent;

public class JSPPageUtility extends PageParentClass
{
	public boolean IsKeyValueExist(String tableName, String keyWord, String keyVal)
	{
		DBTableParent hTBHandle = new DatabaseStore(tableName);
		hTBHandle.QueryRecordByFilterKeyList(Arrays.asList(keyWord), Arrays.asList(keyVal));
		if (hTBHandle.getTableInstance().RecordDBCount() > 0)
			return true;
		return false;
	}
}
