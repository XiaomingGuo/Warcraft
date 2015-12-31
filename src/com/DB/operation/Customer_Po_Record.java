package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.CustomerPoRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Customer_Po_Record extends DBTableParent implements ITableInterface
{
	private List<CustomerPoRecord> resultList = null;
	private CustomerPoRecord aWriteRecord = null;
	
	public Customer_Po_Record(IEQManagement hEQMHandle)
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
		Iterator<CustomerPoRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			CustomerPoRecord tempRecord = (CustomerPoRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "po_name":
				rtnRst.add(tempRecord.getPoName());
				break;
			case "delivery_date":
				rtnRst.add(tempRecord.getDeliveryDate());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "OUT_QTY":
				rtnRst.add(tempRecord.getOutQty().toString());
				break;
			case "vendor":
				rtnRst.add(tempRecord.getVendor());
				break;
			case "percent":
				rtnRst.add(tempRecord.getPercent().toString());
				break;
			case "create_date":
				rtnRst.add(tempRecord.getCreateDate().toString());
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
		execQueryAsc("poName", poName, "id");
	}
	
	private void execQueryAsc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from CustomerPoRecord cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
		getEQMHandle().EQQuery(hql);
	}
	
	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from CustomerPoRecord cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new CustomerPoRecord();
		aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("bar_code") >= 0) {
			rtnRst = "barCode";
		}
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("delivery_date") >= 0) {
			rtnRst = "deliveryDate";
		}
		else if(keyword.toLowerCase().indexOf("qty") >= 0) {
			rtnRst = "qty";
		}
		else if(keyword.toLowerCase().indexOf("out_qty") >= 0) {
			rtnRst = "outQty";
		}
		else if(keyword.toLowerCase().indexOf("vendor") >= 0) {
			rtnRst = "vendor";
		}
		else if(keyword.toLowerCase().indexOf("percent") >= 0) {
			rtnRst = "percent";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
	{
		for (String item : delList)
		{
			String hql = String.format("delete CustomerPoRecord cpr where cpr.%s='%s'", GetDatabaseKeyWord(keyWord), item);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		// TODO Auto-generated method stub
		
	}

}
