package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.ShippingNo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Shipping_No extends DBTableParent implements ITableInterface
{
	private List<ShippingNo> resultList = null;
	private ShippingNo aWriteRecord = null;
	
	public Shipping_No(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<ShippingNo> it = resultList.iterator();
		while(it.hasNext())
		{
			ShippingNo tempRecord = (ShippingNo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				break;
			case "name":
				break;
			case "product_type":
				break;
			case "weight":
				break;
			case "description":
				break;
			case "picture":
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

	public void GetRecordByName(String name)
	{
		String hql = String.format("from ShippingNo pi where pi.name='%s'", name);
		getEQMHandle().EQQuery(hql);
	}
}
