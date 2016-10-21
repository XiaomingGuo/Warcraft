package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.OverTimeRecord;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Over_Time_Record implements ITableInterface
{
	private List<OverTimeRecord> resultList = null;
	private OverTimeRecord aWriteRecord = null;
	IEQManagement gEQMHandle;
	
	public Over_Time_Record(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
	@Override
	public String GetTableName()
	{
		return "OverTimeRecord";
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
		Iterator<OverTimeRecord> it = resultList.iterator();
		while(it.hasNext())
		{
			OverTimeRecord tempRecord = (OverTimeRecord)it.next();
			switch (keyWord)
			{
			case "id":
				rtnRst.add(tempRecord.getId().toString());
				break;
			case "check_in_id":
				rtnRst.add(tempRecord.getCheckInId());
				break;
			case "over_time_date":
				rtnRst.add(tempRecord.getOverTimeDate());
				break;
			case "over_time_hour":
				rtnRst.add(tempRecord.getOverTimeHour());
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
	
	public void AddARecord(String checkInId, String overTimeDate, String overTimeHour)
	{
		aWriteRecord = new OverTimeRecord();
		aWriteRecord.setCheckInId(checkInId);
		aWriteRecord.setOverTimeDate(overTimeDate);
		aWriteRecord.setOverTimeHour(overTimeHour);
		gEQMHandle.addANewRecord();
	}
	
	@Override
	public String GetDatabaseKeyWord(String keyword) {
		String rtnRst = "";
		if(keyword.toLowerCase().indexOf("id") == 0){
			rtnRst = "id";
		}
		else if(keyword.toLowerCase().indexOf("check_in_id") == 0) {
			rtnRst = "checkInId";
		}
		else if(keyword.toLowerCase().indexOf("over_time_date") == 0) {
			rtnRst = "overTimeDate";
		}
		else if(keyword.toLowerCase().indexOf("over_time_hour") == 0) {
			rtnRst = "overTimeHour";
		}
		return rtnRst;
	}
}
