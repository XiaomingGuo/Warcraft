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
	public String GetDatabaseKeyWord(String keyword)
	{
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("name") >= 0) {
			rtnRst = "name";
		}
		return rtnRst;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		// TODO Auto-generated method stub
		
	}

}
