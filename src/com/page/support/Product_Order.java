package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.ProductOrder;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Product_Order extends DBTableParent implements ITableInterface
{
	private List<ProductOrder> resultList = null;
	
	public Product_Order(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ProductOrder> it = getResultList().iterator();
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
	public void setResult(Query query)
	{
		this.setResultList(query.list());
	}

	public List<ProductOrder> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<ProductOrder> resultList)
	{
		this.resultList = resultList;
	}
	
	public List<String> GetOrderNameByStatus(int iStatus)
	{
		String hql = String.format("from ProductOrder po where po.status='%d'", iStatus);
		getEQMHandle().EQQuery(hql);
		return getDBRecordList("Order_Name");
	}

}
