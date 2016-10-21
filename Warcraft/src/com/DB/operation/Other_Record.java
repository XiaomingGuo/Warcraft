package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.OtherRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Other_Record implements ITableInterface
{
	private List<OtherRecord> resultList = null;
	private OtherRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Other_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "OtherRecord";
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

	public void AddARecord(String appBarcode, String proposerName, String appProduct_QTY, String appUserName, String mergeMark)
	{
		aWriteRecord = new OtherRecord();
		aWriteRecord.setBarCode(appBarcode);
		aWriteRecord.setProposer(proposerName);
		aWriteRecord.setQty(Integer.parseInt(appProduct_QTY));
		aWriteRecord.setUserName(appUserName);
		aWriteRecord.setMergeMark(Integer.parseInt(mergeMark));
		gEQMHandle.addANewRecord();
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
		else if(keyword.toLowerCase().indexOf("batch_lot") >= 0) {
			rtnRst = "batchLot";
		}
		else if(keyword.toLowerCase().indexOf("proposer") >= 0) {
			rtnRst = "proposer";
		}
		else if(keyword.toLowerCase().indexOf("qty") >= 0) {
			rtnRst = "qty";
		}
		else if(keyword.toLowerCase().indexOf("user_name") >= 0) {
			rtnRst = "userName";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		else if(keyword.toLowerCase().indexOf("isapprove") >= 0) {
			rtnRst = "isApprove";
		}
		else if(keyword.toLowerCase().indexOf("merge_mark") >= 0) {
			rtnRst = "mergeMark";
		}
		return rtnRst;
	}
}
