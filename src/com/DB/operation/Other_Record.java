package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.OtherRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Other_Record extends DBTableParent implements ITableInterface
{
	private List<OtherRecord> resultList = null;
	private OtherRecord aWriteRecord = null;
	
	public Other_Record(IEQManagement hEQMHandle)
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
		Iterator<OtherRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			OtherRecord tempRecord = (OtherRecord)it.next();
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
			case "user_name":
				rtnRst.add(tempRecord.getUserName());
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

	public void AddARecord(String appBarcode, String proposerName, String appProduct_QTY, String appUserName)
	{
		aWriteRecord = new OtherRecord();
		aWriteRecord.setBarCode(appBarcode);
		aWriteRecord.setProposer(proposerName);
		aWriteRecord.setQty(Integer.parseInt(appProduct_QTY));
		aWriteRecord.setUserName(appUserName);
		getEQMHandle().addANewRecord();
	}
	
	public void AddMRecord(String[] appBarcode, String[] proposerName, String[] appProduct_QTY, String[] appUserName)
	{
		
	}
}
