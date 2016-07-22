package com.DB.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.DB.support.HolidayMark;
import com.Warcraft.Interface.IEQManagement;
import com.Warcraft.Interface.ITableInterface;
import com.Warcraft.SupportUnit.DBTableParent;

public class Holiday_Mark extends DBTableParent implements ITableInterface
{
    private List<HolidayMark> resultList = null;
    private HolidayMark aWriteRecord = null;
    
    public Holiday_Mark(IEQManagement hEQMHandle)
    {
        super(hEQMHandle);
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
    
    public void AddARecord(String checkInId, String holidayDate)
    {
        aWriteRecord = new HolidayMark();
        aWriteRecord.setCheckInId(checkInId);
        aWriteRecord.setHolidayDate(holidayDate);
        getEQMHandle().addANewRecord();
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
        return rtnRst;
    }
}
