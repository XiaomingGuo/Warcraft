package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import java.sql.Time;

import com.DB.support.WorkGroupInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Work_Group_Info implements ITableInterface
{
	private List<WorkGroupInfo> resultList = null;
	private WorkGroupInfo aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Work_Group_Info(){}
	
	@Override
	public void setEQManagement(IEQManagement hEQHandle)
	{
		gEQMHandle = hEQHandle;
	}
	
	@Override
	public String GetTableName()
	{
		return "WorkGroupInfo";
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
		Iterator<WorkGroupInfo> it = resultList.iterator();
		while(it.hasNext())
		{
			WorkGroupInfo tempRecord = (WorkGroupInfo)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "group_name":
				rtnRst.add(tempRecord.getGroupName());
				break;
			case "check_in_time":
				rtnRst.add(tempRecord.getCheckInTime().toString());
				break;
			case "check_out_time":
				rtnRst.add(tempRecord.getCheckOutTime().toString());
				break;
			case "work_days_aweek":
				rtnRst.add(tempRecord.getWorkDaysAweek().toString());
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
	
	public void AddARecord(String groupName, String checkInTime, String checkOutTime, String workDaysAweek)
	{
		aWriteRecord = new WorkGroupInfo();
		aWriteRecord.setGroupName(groupName);
		aWriteRecord.setCheckInTime(Time.valueOf(checkInTime));
		aWriteRecord.setCheckOutTime(Time.valueOf(checkOutTime));
		aWriteRecord.setWorkDaysAweek(Integer.parseInt(workDaysAweek));
		gEQMHandle.addANewRecord();
	}

	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") >= 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("group_name") >= 0) {
			rtnRst = "groupName";
		}
		else if(keyword.toLowerCase().indexOf("check_in_time") >= 0) {
			rtnRst = "checkInTime";
		}
		else if(keyword.toLowerCase().indexOf("check_out_time") >= 0) {
			rtnRst = "checkOutTime";
		}
		else if(keyword.toLowerCase().indexOf("work_days_aweek") >= 0) {
			rtnRst = "workDaysAweek";
		}
		return rtnRst;
	}
}
