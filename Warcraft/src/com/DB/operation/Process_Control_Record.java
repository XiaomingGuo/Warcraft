package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProcessControlRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Process_Control_Record implements ITableInterface
{
	private List<ProcessControlRecord> resultList = null;
	private ProcessControlRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Process_Control_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "ProcessControlRecord";
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
		Iterator<ProcessControlRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			ProcessControlRecord tempRecord = (ProcessControlRecord)it.next();
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
			case "process_id":
				rtnRst.add(tempRecord.getProcessId().toString());
				break;
			case "QTY":
				rtnRst.add(tempRecord.getQty().toString());
				break;
			case "operator":
				rtnRst.add(tempRecord.getOperator());
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
	
	public void AddARecord(String barCode, String poName, String processId, String qty, String operator)
	{
		aWriteRecord = new ProcessControlRecord();
		aWriteRecord.setBarCode(barCode);
		aWriteRecord.setPoName(poName);
		aWriteRecord.setProcessId(Integer.parseInt(processId));
		aWriteRecord.setQty(Integer.parseInt(qty));
		aWriteRecord.setOperator(operator);
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
		else if(keyword.toLowerCase().indexOf("po_name") >= 0) {
			rtnRst = "poName";
		}
		else if(keyword.toLowerCase().indexOf("process_id") >= 0) {
			rtnRst = "processId";
		}
		else if(keyword.toLowerCase().indexOf("qty") >= 0) {
			rtnRst = "qty";
		}
		else if(keyword.toLowerCase().indexOf("operator") >= 0) {
			rtnRst = "operator";
		}
		else if(keyword.toLowerCase().indexOf("create_date") >= 0) {
			rtnRst = "createDate";
		}
		return rtnRst;
	}
}
