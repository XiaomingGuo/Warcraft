package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ShippingNo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Shipping_No implements ITableInterface
{
	private List<ShippingNo> resultList = null;
	private ShippingNo aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Shipping_No(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "ShippingNo";
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
		Iterator<ShippingNo> it = resultList.iterator();
		while(it.hasNext())
		{
			ShippingNo tempRecord = (ShippingNo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "customer_po":
				rtnRst.add(tempRecord.getCustomerPo());
				break;
			case "create_date":
				rtnRst.add(tempRecord.getCreateDate().toString());
				break;
			case "shipping_no":
				rtnRst.add(tempRecord.getShippingNo().toString());
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
	
	public void AddARecord(String customerPo, String shippingNo)
	{
		aWriteRecord = new ShippingNo();
		aWriteRecord.setCustomerPo(customerPo);
		aWriteRecord.setShippingNo(Integer.parseInt(shippingNo));
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("customer_po") >= 0) {
			rtnRst = "customerPo";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "create_date";
		}
		else if(keyword.toLowerCase().indexOf("shipping_no") >= 0) {
			rtnRst = "shippingNo";
		}
		return rtnRst;
	}
}
