package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.MaterialRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Material_Record extends DBTableParent implements ITableInterface
{
	private List<MaterialRecord> resultList = null;
	private MaterialRecord aWriteRecord = null;
	
	public Material_Record(IEQManagement hEQMHandle)
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
		Iterator<MaterialRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			MaterialRecord tempRecord = (MaterialRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "Bar_Code":
				rtnRst.add(tempRecord.getBarCode());
				break;
			case "Batch_Lot":
				rtnRst.add(tempRecord.getBatchLot());
				break;
			case "proposer":
				rtnRst.add(tempRecord.getProposer());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "create_date":
				rtnRst.add(tempRecord.getCreateDate().toString());
				break;
			case "isApprove":
				rtnRst.add(tempRecord.getIsApprove().toString());
				break;
			case "Merge_Mark":
				rtnRst.add(tempRecord.getMergeMark().toString());
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
		String hql = String.format("from MaterialRecord cpr where cpr.%s='%s' order by cpr.%s asc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	
	/*
	private void execQueryDesc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from MaterialRecord cpr where cpr.%s='%s' order by cpr.%s desc", keyWord, value, orderKey);
		getEQMHandle().EQQuery(hql);
	}
	*/
	
	public void AddARecord(String poName)
	{
		aWriteRecord = new MaterialRecord();
		//aWriteRecord.setPoName(poName);
		getEQMHandle().addANewRecord();
	}

	@Override
	public double GetDblSumOfValue(String getValue, String keyword, String keyValue)
	{
		//return super.GetDblSumOfValue(storage_name, getValue, keyword, keyValue);
		return 0;
	}
	
	@Override
	public int GetIntSumOfValue(String getValue, String keyword, String keyValue)
	{
		//return super.GetIntSumOfValue(storage_name, getValue, keyword, keyValue);
		return 0;
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateRecordByKeyWord(String setKeyWord, String setValue,
			String keyWord, String keyValue) {
		// TODO Auto-generated method stub
		
	}

}
