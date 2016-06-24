package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import java.sql.Time;

import com.DB.support.WorkGroupInfo;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Work_Group_Info extends DBTableParent implements ITableInterface
{
	private List<WorkGroupInfo> resultList = null;
	private WorkGroupInfo aWriteRecord = null;
	
	public Work_Group_Info(IEQManagement hEQMHandle)
	{
		super(hEQMHandle);
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
		String hql = String.format("from WorkGroupInfo cpr where cpr.%s='%s' order by cpr.%s asc", GetDatabaseKeyWord(keyWord), value, GetDatabaseKeyWord(orderKey));
		getEQMHandle().EQQuery(hql);
	}
	
	public void QueryRecordOrderByIdASC(String po_name)
	{
		String hql = String.format("from WorkGroupInfo cpr where cpr.poName='%s' order by cpr.id asc", po_name);
		getEQMHandle().EQQuery(hql);
	}
	
	public void AddARecord(String groupName, String checkInTime, String checkOutTime, String qty, String vendor, String percent)
	{
		aWriteRecord = new WorkGroupInfo();
		aWriteRecord.setGroupName(groupName);
		aWriteRecord.setCheckInTime(Time.valueOf(checkInTime));
		aWriteRecord.setCheckOutTime(Time.valueOf(checkOutTime));
		getEQMHandle().addANewRecord();
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
		return rtnRst;
	}

	@Override
	public void DeleteRecordByKeyWord(String keyWord, List<String> delList)
	{
		for (String item : delList)
		{
			String hql = String.format("delete WorkGroupInfo cpr where cpr.%s='%s'", GetDatabaseKeyWord(keyWord), item);
			getEQMHandle().DeleteAndUpdateRecord(hql);
		}
	}
}
