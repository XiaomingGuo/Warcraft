package com.page.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.operation.MbMaterialPoRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Mb_Material_Po_Record extends DBTableParent implements ITableInterface
{
	private List<MbMaterialPoRecord> resultList = null;
	private MbMaterialPoRecord aWriteRecord = null;
	
	public Mb_Material_Po_Record(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}

	@Override
	public List<String> getDBRecordList(String keyWord)
	{
		List<String> rtnRst = new ArrayList<String>();
		Iterator<MbMaterialPoRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			MbMaterialPoRecord tempRecord = (MbMaterialPoRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				break;
			case "po_name":
				break;
			case "delivery_date":
				break;
			case "QTY":
				break;
			case "OUT_QTY":
				break;
			case "vendor":
				break;
			case "percent":
				break;
			case "create_date":
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
		String hql = String.format("from MbMaterialPoRecord cpr where cpr.%s='%s' order by cpr.%s asc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	
	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from MbMaterialPoRecord cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new MbMaterialPoRecord();
		//aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

}
