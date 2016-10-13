package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.CustomerPo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Customer_Po implements ITableInterface
{
	private List<CustomerPo> resultList = null;
	private CustomerPo aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Customer_Po(IEQManagement hEQMHandle)
	{
		gEQMHandle = hEQMHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "CustomerPo";
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
	
	public void QueryRecordByPoNameAndMoreThanStatus(String poName, String status)
	{
		String hql = String.format("from CustomerPo cp where cp.poName = '%s' and cp.status > %s", poName, status);
		gEQMHandle.EQQuery(hql);
	}
	
	public void GetRecordLessThanStatus(int istatus)
	{
		String hql = String.format("from CustomerPo cp where cp.status<='%d'", istatus);
		gEQMHandle.EQQuery(hql);
	}
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new CustomerPo();
		aWriteRecord.setPoName(poName);
		gEQMHandle.addANewRecord();
	}
	
	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("status") >= 0) {
			rtnRst = "status";
		}
		return rtnRst;
	}
}
