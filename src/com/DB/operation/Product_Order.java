package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

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

	public void GetRecordByStatus(int iStatus)
	{
		String hql = String.format("from ProductOrder po where po.status='%d'", iStatus);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordLessThanStatus(int iStatus)
	{
		String hql = String.format("from ProductOrder po where po.status<='%d'", iStatus);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordMoreThanStatus(int iStatus)
	{
		String hql = String.format("from ProductOrder po where po.status>='%d'", iStatus);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordMoreThanAndLessThanStatus(int iMoreThanStatus, int iLessThanStatus)
	{
		String hql = String.format("from ProductOrder po where po.status>'%d' and po.status < '%d'", iMoreThanStatus, iLessThanStatus);
		getEQMHandle().EQQuery(hql);
	}
	
	public void GetRecordByOrderName(String orderName)
	{
		String hql = String.format("from ProductOrder po where po.orderName='%s'", orderName);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String orderName)
	{
		aWriteRecord = new ProductOrder();
		aWriteRecord.setOrderName(orderName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("order_name") >= 0) {
			rtnRst = "orderName";
		}
		else if(keyword.toLowerCase().indexOf("status") >= 0) {
			rtnRst = "status";
		}
		return rtnRst;
	}
	
	public void DeleteRecordByKeyWord(String keyWord, List<String> delPoList)
	{
		for (String orderName : delPoList)
		{
			String hql = String.format("delete ProductOrder po where po.%s='%s'", GetDatabaseKeyWord(keyWord), orderName);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}
	
	public void UpdatetRecordByOrderName(String orderName, String keyWord, String value)
	{
		String hql = String.format("update ProductOrder po set po.%s='%s' where po.orderName='%s'", GetDatabaseKeyWord(keyWord), value, orderName);
		getEQMHandle().DeleteAndUpdateRecord(hql);
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		String hql = String.format("update ProductOrder po set po.%s='%s' where po.%s='%s'", GetDatabaseKeyWord(setKeyWord), setValue, GetDatabaseKeyWord(keyWord), keyValue);
		getEQMHandle().DeleteAndUpdateRecord(hql);
		
	}
}
