package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.HolidayMark;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;

public class Holiday_Mark implements ITableInterface
{
    private List<HolidayMark> resultList = null;
    private HolidayMark aWriteRecord = null;
	IEQManagement gEQMHandle;
    
    public Holiday_Mark(){}
    
    @Override
    public void setEQManagement(IEQManagement hEQHandle)
    {
        gEQMHandle = hEQHandle;
    }
    
    @Override
    public String GetTableName()
    {
        return "HolidayMark";
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
        Iterator<HolidayMark> it = resultList.iterator();
        while(it.hasNext())
        {
            HolidayMark tempRecord = (HolidayMark)it.next();
            switch (keyWord)
            {
            case "id":
                rtnRst.add(tempRecord.getId().toString());
                break;
            case "check_in_id":
                rtnRst.add(tempRecord.getCheckInId());
                break;
            case "holiday_date":
                rtnRst.add(tempRecord.getHolidayDate());
                break;
            case "holiday_info":
                rtnRst.add(tempRecord.getHolidayInfo());
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
    
    public void AddARecord(String checkInId, String holidayDate, String holidayInfo)
    {
        aWriteRecord = new HolidayMark();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setHolidayDate(holidayDate);
        aWriteRecord.setHolidayInfo(holidayInfo);
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
        else if(keyword.toLowerCase().indexOf("holiday_date") >= 0) {
            rtnRst = "holidayDate";
        }
        else if(keyword.toLowerCase().indexOf("holiday_info") >= 0) {
            rtnRst = "holidayInfo";
        }
        return rtnRst;
    }
}
