package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.ExhaustedOther;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Exhausted_Other extends DBTableParent implements ITableInterface
{
	private List<ExhaustedOther> resultList = null;
	private ExhaustedOther aWriteRecord = null;
	
	public Exhausted_Other(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ExhaustedOther> it = resultList.iterator();
		while(it.hasNext())
		{
			ExhaustedOther tempRecord = (ExhaustedOther)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "name":
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
		String hql = String.format("from ExhaustedOther");
		getEQMHandle().EQQuery(hql);
	}

}
