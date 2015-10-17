package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.CustomerPo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Customer_Po extends DBTableParent implements ITableInterface
{
	private List<CustomerPo> resultList = null;
	private CustomerPo aWriteRecord = null;
	
	public Customer_Po(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<CustomerPo> it = resultList.iterator();
		while(it.hasNext())
		{
			CustomerPo tempRecord = (CustomerPo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
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

	public void GetRecordByPoName(String poName)
	{
		execQuery("poName", poName);
	}
	
	public void GetRecordByStatus(int istatus)
	{
		execQuery("status", Integer.toString(istatus));
	}
	
	private void execQuery(String keyWord, String value)
	{
		String hql = String.format("from CustomerPo cp where cp.%s='%s'", keyWord, value);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new CustomerPo();
		aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

}
