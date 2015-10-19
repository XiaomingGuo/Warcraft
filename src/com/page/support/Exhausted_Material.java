package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.ExhaustedMaterial;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Exhausted_Material extends DBTableParent implements ITableInterface
{
	private List<ExhaustedMaterial> resultList = null;
	private ExhaustedMaterial aWriteRecord = null;
	
	public Exhausted_Material(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ExhaustedMaterial> it = resultList.iterator();
		while(it.hasNext())
		{
			ExhaustedMaterial tempRecord = (ExhaustedMaterial)it.next();
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
		String hql = String.format("from ExhaustedMaterial");
		getEQMHandle().EQQuery(hql);
	}

}
