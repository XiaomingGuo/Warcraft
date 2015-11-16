package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductType;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Type extends DBTableParent implements ITableInterface
{
	private List<ProductType> resultList = null;
	private ProductType aWriteRecord = null;
	
	public Product_Type(IEQManagement hEQMHandle)
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
		Iterator<ProductType> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductType tempRecord = (ProductType)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "name":
				rtnRst.add(tempRecord.getName());
				break;
			case "storeroom":
				rtnRst.add(tempRecord.getStoreroom());
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

	public void GetRecordByStoreroom(String storeName)
	{
		String hql = String.format("from ProductType pt where pt.storeroom='%s'", storeName);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByName(String name)
	{
		String hql = String.format("from ProductType pt where pt.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String proType, String storeroom)
	{
		aWriteRecord = new ProductType();
		aWriteRecord.setName(proType);
		aWriteRecord.setStoreroom(storeroom);
		getEQMHandle().addANewRecord();
	}

}
