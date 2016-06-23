package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.ProcessInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Process_Info extends DBTableParent implements ITableInterface
{
	private List<ProcessInfo> resultList = null;
	private ProcessInfo aWriteRecord = null;
	
	public Process_Info(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
	}
	
	@Override
	public String GetTableName()
	{
		return "ProcessInfo";
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
		Iterator<ProcessInfo> it = resultList.iterator();
		while(it.hasNext())
		{
			ProcessInfo tempRecord = (ProcessInfo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "process_name":
				rtnRst.add(tempRecord.getProcessName());
				break;
			case "station_name":
				rtnRst.add(tempRecord.getStationName());
				break;
			case "process_order":
				rtnRst.add(tempRecord.getProcessOrder().toString());
				break;
			case "process_count":
				rtnRst.add(tempRecord.getProcessCount().toString());
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
		execQueryAsc("po_name", poName, "id");
	}
	
	private void execQueryAsc(String keyWord, String value, String orderKey)
	{
		String hql = String.format("from ProcessInfo cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
		getEQMHandle().EQQuery(hql);
	}
	
	public void QueryRecordOrderByIdASC(String po_name)
	{
		String hql = String.format("from ProcessInfo cpr where cpr.poName='%s' order by cpr.id asc", po_name);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String processName, String stationName, String processOrder, String processCount, String vendor, String percent)
	{
		aWriteRecord = new ProcessInfo();
		aWriteRecord.setProcessName(processName);
		aWriteRecord.setStationName(stationName);
		aWriteRecord.setProcessOrder(Integer.parseInt(processOrder));
		aWriteRecord.setProcessCount(Integer.parseInt(processCount));
		getEQMHandle().addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("process_name") >= 0) {
			rtnRst = "processName";
		}
		else if(keyword.toLowerCase().indexOf("station_name") >= 0) {
			rtnRst = "stationName";
		}
		else if(keyword.toLowerCase().indexOf("process_order") >= 0) {
			rtnRst = "processOrder";
		}
		else if(keyword.toLowerCase().indexOf("process_count") >= 0) {
			rtnRst = "processCount";
		}
		return rtnRst;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
	{
		for (String item : delList)
		{
			String hql = String.format("delete ProcessInfo cpr where cpr.%s='%s'", GetDatabaseKeyWord(keyWord), item);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}
}
