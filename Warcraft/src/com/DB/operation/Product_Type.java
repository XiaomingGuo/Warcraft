package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProductType;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Type implements ITableInterface
{
	private List<ProductType> resultList = null;
	private ProductType aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Product_Type(IEQManagement hEQMHandle)
	{
		gEQMHandle = hEQMHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "ProductType";
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
	
	public void AddARecord(String proType, String storeroom)
	{
		aWriteRecord = new ProductType();
		aWriteRecord.setName(proType);
		aWriteRecord.setStoreroom(storeroom);
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("name") >= 0) {
			rtnRst = "name";
		}
		else if(keyword.toLowerCase().indexOf("storeroom") >= 0) {
			rtnRst = "storeroom";
		}
		return rtnRst;
	}
}
