package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.Interface.IEQManagement;

public class Product_Order implements ITableInterface
{
	private IEQManagement hEQMHandle = null;
	private List<ProductOrder> resultList = null;
	
	public Product_Order(IEQManagement hEQMHandle)
	{
		this.hEQMHandle = hEQMHandle;
		this.hEQMHandle.setTableHandle(this);
	}
	
	public List<String> GetOrderNameByStatus(int iStatus)
	{
		List<String> rtnRst = new ArrayList<String>();
		String hql = String.format("from ProductOrder po where po.status='%d'", iStatus);
		hEQMHandle.EQQuery(hql);
		Iterator<ProductOrder> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductOrder tempRecord = (ProductOrder)it.next();
			rtnRst.add(tempRecord.getOrderName());
		}
		return rtnRst;
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		return null;
	}

	@Override
	public IEQManagement gethEQMHandle()
	{
		return this.hEQMHandle;
	}

	@Override
	public void sethEQMHandle(IEQManagement hEQMHandle)
	{
		this.hEQMHandle = hEQMHandle;
	}

	@Override
	public void setResult(Query query)
	{
		this.resultList = query.list();
		
	}

	public List<ProductOrder> getQueryResultList()
	{
		return this.resultList;
	}

	public void setResultList(List<ProductOrder> ResultList)
	{
		this.resultList = ResultList;
	}
}
