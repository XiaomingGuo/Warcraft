package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.CustomerPoRecord;
import com.DB.support.ProductOrder;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Order extends DBTableParent implements ITableInterface
{
	private List<ProductOrder> resultList = null;
	private ProductOrder aWriteRecord = null;
	
	public Product_Order(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ProductOrder> it = resultList.iterator();
		while(it.hasNext())
		{
			ProductOrder tempRecord = (ProductOrder)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Order_Name":
				rtnRst.add(tempRecord.getOrderName());
				break;
			case "status":
				rtnRst.add(tempRecord.getStatus().toString());
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

	public void GetOrderNameByStatus(int iStatus)
	{
		String hql = String.format("from ProductOrder po where po.status='%d'", iStatus);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByOrderName(String orderName)
	{
		String hql = String.format("from ProductOrder po where po.orderName='%d'", orderName);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String orderName)
	{
		aWriteRecord = new ProductOrder();
		aWriteRecord.setOrderName(orderName);
		getEQMHandle().addANewRecord();
	}

}
