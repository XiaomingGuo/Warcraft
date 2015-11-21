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
	
	public void GetRecordByName(String name)
	{
		String hql = String.format("from StoreroomName sn where sn.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}

	public void AddARecord(String poName)
	{
		aWriteRecord = new StoreroomName();
		aWriteRecord.setName(poName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public int GetIntSumOfValue(String getValue,
			String keyword, String keyValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double GetDblSumOfValue(String getValue,
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
