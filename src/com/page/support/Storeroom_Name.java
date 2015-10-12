package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.StoreroomName;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Storeroom_Name extends DBTableParent implements ITableInterface
{
	private List<StoreroomName> resultList = null;
	
	public Storeroom_Name(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<StoreroomName> it = getResultList().iterator();
		while(it.hasNext())
		{
			StoreroomName tempRecord = (StoreroomName)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "name":
				rtnRst.add(tempRecord.getName());
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

	public List<StoreroomName> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<StoreroomName> resultList)
	{
		this.resultList = resultList;
	}
	
	public void GetAllRecord()
	{
		String hql = String.format("from StoreroomName");
		getEQMHandle().EQQuery(hql);
	}

}
