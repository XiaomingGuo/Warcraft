package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.StoreroomName;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Storeroom_Name extends DBTableParent implements ITableInterface
{
	private List<StoreroomName> resultList = null;
	private StoreroomName aWriteRecord = null;
	
	public Storeroom_Name(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<StoreroomName> it = resultList.iterator();
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
	public void setResultList(Query query)
	{
		this.resultList = query.list();
	}

	@Override
	public Object getAWriteRecord()
	{
		return aWriteRecord;
	}
	
	public void GetAllRecord()
	{
		String hql = String.format("from StoreroomName");
		getEQMHandle().EQQuery(hql);
	}

}
