package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.UserInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class User_Info extends DBTableParent implements ITableInterface
{
	private List<UserInfo> resultList = null;
	private UserInfo aWriteRecord = null;
	
	public User_Info(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public int RecordDBCount()
	{
		int rtnRst = 0;
		if (resultList != null)
			rtnRst = resultList.size();
		return rtnRst;
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<UserInfo> it = resultList.iterator();
		while(it.hasNext())
		{
			UserInfo tempRecord = (UserInfo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "name":
				rtnRst.add(tempRecord.getName());
				break;
			case "password":
				rtnRst.add(tempRecord.getPassword());
				break;
			case "create_date":
				rtnRst.add(tempRecord.getCreateDate().toString());
				break;
			case "department":
				rtnRst.add(tempRecord.getDepartment());
				break;
			case "permission":
				rtnRst.add(tempRecord.getPermission().toString());
				break;
			case "picture":
				rtnRst.add(null);
				break;
			default:
				break;
			}
		}
		return rtnRst;
	}

	@Override
	public void setResultList(Query query)
	{
		this.resultList = query.list();
	}
	
	@Override
	public Object getAWriteRecord()
	{
		return aWriteRecord;
	}

	public void GetRecordByName(String name)
	{
		String hql = String.format("from UserInfo ui where ui.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}

	@Override
	public int GetIntSumOfValue(String storage_name, String getValue,
			String keyword, String keyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double GetDblSumOfValue(String storage_name, String getValue,
			String keyword, String keyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}