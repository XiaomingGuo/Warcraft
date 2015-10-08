package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.UserInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class User_Info extends DBTableParent implements ITableInterface
{
	private List<UserInfo> resultList = null;
	
	public User_Info(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<UserInfo> it = getResultList().iterator();
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
	public void setResult(Query query)
	{
		this.setResultList(query.list());
	}

	public List<UserInfo> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<UserInfo> resultList)
	{
		this.resultList = resultList;
	}
	
	public String GetPasswordByName(String name)
	{
		String hql = String.format("from UserInfo ui where ui.name='%s'", name);
		getEQMHandle().EQQuery(hql);
		return getDBRecordList("password").get(0);
	}

}
